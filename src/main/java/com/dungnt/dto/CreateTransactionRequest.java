package com.dungnt.dto;

import java.util.List;

public class CreateTransactionRequest {
    private String transAmount;
    private String description;
    private String orderId;
    private String otherInfo;
    private List<String> returnType;
    private String returnUrl;
    private String cancelUrl;
    private String paymentMethod;
    private Integer expireAfter;

}
