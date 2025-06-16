package br.com.support.car_dealer_api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object value) {
        super(String.format("%s not found with %s = '%s'", resourceName, fieldName, value));
    }
}
