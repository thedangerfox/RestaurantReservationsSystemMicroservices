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

class CustomerEligibilityRestAdapterTest {

    private RestTemplate rest;
    private CustomerEligibilityRestAdapter adapter;

    @BeforeEach
    void setup() {
        rest = mock(RestTemplate.class);
        adapter = new CustomerEligibilityRestAdapter(rest);
        ReflectionTestUtils.setField(adapter, "customersBaseUrl", "http://customers");
    }

    @Test
    void returnsTrueWhenServiceReturnsTrue() {
        when(rest.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        assertThat(adapter.isEligible(1L)).isTrue();
    }

    @Test
    void returnsFalseWhenServiceReturnsFalse() {
        when(rest.getForObject(anyString(), eq(Boolean.class))).thenReturn(false);
        assertThat(adapter.isEligible(1L)).isFalse();
    }

    @Test
    void returnsFalseWhenServiceReturnsNull() {
        when(rest.getForObject(anyString(), eq(Boolean.class))).thenReturn(null);
        assertThat(adapter.isEligible(1L)).isFalse();
    }

    @Test
    void notFoundThrowsCrossContextValidationException() {
        when(rest.getForObject(anyString(), eq(Boolean.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        assertThatThrownBy(() -> adapter.isEligible(1L))
                .isInstanceOf(CrossContextValidationException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void clientErrorThrowsCrossContextValidationException() {
        when(rest.getForObject(anyString(), eq(Boolean.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad", null, null, null));

        assertThatThrownBy(() -> adapter.isEligible(1L))
                .isInstanceOf(CrossContextValidationException.class)
                .hasMessageContaining("validation failed");
    }

    @Test
    void unknownErrorThrowsCrossContextValidationException() {
        when(rest.getForObject(anyString(), eq(Boolean.class)))
                .thenThrow(new RuntimeException("connection refused"));

        assertThatThrownBy(() -> adapter.isEligible(1L))
                .isInstanceOf(CrossContextValidationException.class)
                .hasMessageContaining("unavailable");
    }
}
