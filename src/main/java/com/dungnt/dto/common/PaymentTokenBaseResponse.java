package com.dungnt.dto.common;

import lombok.Data;

@Data
public class PaymentTokenBaseResponse {
    private String requestId;
    private String orderId;
    private Long transAmount;
    private Long fee;
    private Long discount;
    private Long totalAmount;
    private Long vtRequestId;
    private Integer transactionStatus;
    private String otp;
    public static class PaymentInstrument {
        private String paymentInstrumentId;
        private String phoneNumber;
        private String customerName;
        private String effectiveDate;

    }
}
