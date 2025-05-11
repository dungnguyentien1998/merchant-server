package com.dungnt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTransactionResponse extends PaymentResult {
    private String type;
    private Long feeTransfer;
    private Long feeRefund;
    private Object refundAmount;

}
