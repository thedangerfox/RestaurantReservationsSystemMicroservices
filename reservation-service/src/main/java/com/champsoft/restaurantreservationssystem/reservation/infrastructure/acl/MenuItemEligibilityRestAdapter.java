package com.champsoft.restaurantreservationssystem.reservation.infrastructure.acl;

import com.champsoft.restaurantreservationssystem.reservation.application.exception.CrossContextValidationException;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.MenuItemEligibilityPort;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class MenuItemEligibilityRestAdapter implements MenuItemEligibilityPort {

    private final RestTemplate restTemplate;

    @Value("${services.menu-items.base-url}")
    private String menuItemsBaseUrl;

    public MenuItemEligibilityRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isEligible(Long menuItemId) {
        String url = menuItemsBaseUrl + "/api/menu-items/" + menuItemId + "/eligibility";

        try {
            Boolean result = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(result);

        } catch (HttpClientErrorException.NotFound ex) {
            throw new CrossContextValidationException("Menu item not found: " + menuItemId);

        } catch (HttpClientErrorException ex) {
            throw new CrossContextValidationException("Menu item validation failed: " + menuItemId);

        } catch (Exception ex) {
            throw new CrossContextValidationException("Menu Items service is unavailable.");
        }
    }
}
