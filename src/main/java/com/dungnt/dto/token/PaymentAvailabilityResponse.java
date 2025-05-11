package com.dungnt.dto.token;

import java.util.List;

public class PaymentAvailabilityResponse {
    private String available;
    private List<Detail> detail;
    public static class Detail {
        private String paymentSource;
        private String available;
    }
}
