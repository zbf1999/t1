package com.example.zhaowang.common;

public class ApiResponse {
    private final boolean success;

    public ApiResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
