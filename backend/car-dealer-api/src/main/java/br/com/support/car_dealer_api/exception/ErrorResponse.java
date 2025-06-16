package br.com.support.car_dealer_api.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    public LocalDateTime timestamp;
    public int status;
    public String error;
    public String message;
    public String path;
    public List<String> details;

    public ErrorResponse(int status, String error, String message, String path, List<String> details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.details = details;
    }
}
