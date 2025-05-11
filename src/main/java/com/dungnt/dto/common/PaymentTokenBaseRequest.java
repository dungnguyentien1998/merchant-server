package com.dungnt.dto.common;

import lombok.Data;

@Data
public class PaymentTokenBaseRequest {
    private String transAmount;
    private String description;
    private String orderId;
    private String otherInfo;
    private String token;
    private String merchantUserId;
    private String authType;
    private String requestId;
    private String otp;
}
