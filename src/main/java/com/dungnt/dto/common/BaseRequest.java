package com.dungnt.dto.common;

import lombok.Data;

import java.util.List;

@Data
public class BaseRequest {
    private String orderId;
    private String paymentMethod;
    private List<String> returnType;
    private String returnUrl;
    private String cancelUrl;
    private Integer expireAfter;
}
