package com.dungnt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResult {
    private String orderId;
    private String merchantCode;
    private Long transAmount;
    private Long fee;
    private Long discount;
    private Long totalAmount;
    private Long vtRequestId;
    private Integer transactionStatus;
    private String errorCode;
    private PaymentInstrument paymentInstrument;
    private String paymentMethod;
    private String bank;
    private String signature;
    private List<String> paymentSources;

    public static class PaymentInstrument {
        private String paymentInstrumentId;
        private String cardNumber;
        private String accountNumber;
        private String phoneNumber;
        private String customerName;
        private String effectiveDate;
        private String expiryDate;
        private String cardNetwork;
        private String bankCode;
        private List<String> paymentSource;
    }
}
