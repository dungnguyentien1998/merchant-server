package com.dungnt.dto;

public class PartnerResponse<T> {
    private Status status;
    private T data;

    public static class Status {
        private String code;
        private String message;
    }
}
