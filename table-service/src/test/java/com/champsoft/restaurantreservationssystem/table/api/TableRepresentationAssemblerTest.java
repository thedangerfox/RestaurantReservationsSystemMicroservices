package com.champsoft.restaurantreservationssystem.table.api;

import com.champsoft.restaurantreservationssystem.table.api.dto.TableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;

class TableRepresentationAssemblerTest {

    @Test
    void shouldAddLinks() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        var assembler = new TableRepresentationAssembler();
        var model = assembler.toModel(new TableResponse(1L, 5, 4, "AVAILABLE"));

        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("tables")).isPresent();

        RequestContextHolder.resetRequestAttributes();
    }
}
