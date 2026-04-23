package com.champsoft.restaurantreservationssystem.reservation.infrastructure.acl;

import com.champsoft.restaurantreservationssystem.reservation.application.exception.CrossContextValidationException;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.TableEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class TableEligibilityRestAdapter implements TableEligibilityPort {

    private final RestTemplate restTemplate;

    @Value("${services.tables.base-url}")
    private String tablesBaseUrl;

    public TableEligibilityRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isEligible(Long tableId) {
        String url = tablesBaseUrl + "/api/tables/" + tableId + "/eligibility";

        try {
            Boolean result = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(result);

        } catch (HttpClientErrorException.NotFound ex) {
            throw new CrossContextValidationException("Table not found: " + tableId);

        } catch (HttpClientErrorException ex) {
            throw new CrossContextValidationException("Table validation failed: " + tableId);

        } catch (Exception ex) {
            throw new CrossContextValidationException("Table service is unavailable.");
        }
    }
}
