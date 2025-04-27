package com.dungnt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerResponse<T> {
    private Status status;
    private T data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private String code;
        private String message;
    }
}
