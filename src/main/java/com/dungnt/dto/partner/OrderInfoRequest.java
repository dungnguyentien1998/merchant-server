package com.dungnt.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoRequest {
    private String merchantCode;
    private Long transAmount;
    private String description;
    private String orderId;
    private String otherInfo;

}
