package tko.refresh.dto;

import lombok.Builder;

public class GlobalResponseDto {
    private final String message;
    private final int statusCode;

    @Builder
    public GlobalResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
