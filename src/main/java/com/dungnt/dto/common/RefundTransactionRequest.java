package com.dungnt.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundTransactionRequest {
    private Integer transAmount;
    private String orderId;
    private String originalRequestId;
    private String reason;
}
