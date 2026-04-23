package com.champsoft.restaurantreservationssystem.reservation.infrastructure.acl;

import com.champsoft.restaurantreservationssystem.reservation.application.exception.CrossContextValidationException;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.CustomerEligibilityPort;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerEligibilityRestAdapter implements CustomerEligibilityPort {

  private final RestTemplate restTemplate;

  @Value("${services.customers.base-url}")
  private String customersBaseUrl;

  public CustomerEligibilityRestAdapter(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public boolean isEligible(Long customerId) {
    String url = customersBaseUrl + "/api/customers/" + customerId + "/eligibility";

    try {
      Boolean result = restTemplate.getForObject(url, Boolean.class);
      return Boolean.TRUE.equals(result);

    } catch (HttpClientErrorException.NotFound ex) {
      throw new CrossContextValidationException("Customer not found: " + customerId);

    } catch (HttpClientErrorException ex) {
      throw new CrossContextValidationException("Customer validation failed: " + customerId);

    } catch (Exception ex) {
      throw new CrossContextValidationException("Customer service is unavailable.");
    }
  }
}
