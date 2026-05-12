package com.champsoft.restaurantreservationssystem.menu.api;

import com.champsoft.restaurantreservationssystem.menu.api.dto.MenuItemResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MenuItemRepresentationAssemblerTest {

    @Test
    void shouldAddLinks() {
        var req = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));

        var assembler = new MenuItemRepresentationAssembler();
        var response = new MenuItemResponse(1L, "Pizza", new BigDecimal("12.99"), "ACTIVE");

        var model = assembler.toModel(response);

        assertThat(model.getContent()).isEqualTo(response);
        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("menu-items")).isPresent();
        assertThat(model.getLink("update")).isPresent();
        assertThat(model.getLink("delete")).isPresent();

        RequestContextHolder.resetRequestAttributes();
    }
}
