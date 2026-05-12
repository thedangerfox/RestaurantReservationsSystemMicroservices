package com.champsoft.restaurantreservationssystem.reservation.infrastructure.acl;

import com.champsoft.restaurantreservationssystem.reservation.application.exception.CrossContextValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuItemEligibilityRestAdapterTest {

    private RestTemplate rest;
    private MenuItemEligibilityRestAdapter adapter;

    @BeforeEach
    void setup() {
        rest = mock(RestTemplate.class);
        adapter = new MenuItemEligibilityRestAdapter(rest);
        ReflectionTestUtils.setField(adapter, "menuItemsBaseUrl", "http://menu");
    }

    @Test
    void returnsTrue() {
        when(rest.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        assertThat(adapter.isEligible(1L)).isTrue();
    }

    @Test
    void returnsFalseWhenNull() {
        when(rest.getForObject(anyString(), eq(Boolean.class))).thenReturn(null);
        assertThat(adapter.isEligible(1L)).isFalse();
    }

    @Test
    void notFoundThrows() {
        when(rest.getForObject(anyString(), eq(Boolean.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "NF", null, null, null));

        assertThatThrownBy(() -> adapter.isEligible(1L))
                .isInstanceOf(CrossContextValidationException.class)
                .hasMessageContaining("Menu item not found");
    }

    @Test
    void clientErrorThrows() {
        when(rest.getForObject(anyString(), eq(Boolean.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "BR", null, null, null));

        assertThatThrownBy(() -> adapter.isEligible(1L))
                .isInstanceOf(CrossContextValidationException.class)
                .hasMessageContaining("validation failed");
    }

    @Test
    void unknownErrorThrows() {
        when(rest.getForObject(anyString(), eq(Boolean.class)))
                .thenThrow(new RuntimeException("oops"));

        assertThatThrownBy(() -> adapter.isEligible(1L))
                .isInstanceOf(CrossContextValidationException.class)
                .hasMessageContaining("unavailable");
    }
}
