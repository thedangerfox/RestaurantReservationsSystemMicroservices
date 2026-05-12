package com.champsoft.restaurantreservationssystem.customer.api;

import com.champsoft.restaurantreservationssystem.customer.api.dto.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRepresentationAssemblerTest {

    @Test
    void shouldAddSelfAndCustomersLinks() {
        // Arrange — HATEOAS link building needs a current request bound
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        var assembler = new CustomerRepresentationAssembler();
        var response = new CustomerResponse(1L, "Alice", "555-0001", "ACTIVE");

        // Act
        var model = assembler.toModel(response);

        // Assert
        assertThat(model.getContent()).isEqualTo(response);
        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("customers")).isPresent();

        RequestContextHolder.resetRequestAttributes();
    }
}
