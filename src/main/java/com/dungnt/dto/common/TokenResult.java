package com.dungnt.dto.common;

import java.util.Date;

public class TokenResult extends TokenBaseResult {
    private String channelCode;
    private String merchantCode;
    private String reason;
    private Date cancelDate;
    private PaymentInstrument paymentInstrument;
    public static class PaymentInstrument {
        private String paymentInstrumentId;
        private String phoneNumber;
        private String customerName;
        private String effectiveDate;
    }
}
