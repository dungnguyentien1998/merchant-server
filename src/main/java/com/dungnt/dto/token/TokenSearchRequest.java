package com.dungnt.dto.token;

import lombok.Data;

@Data
public class TokenSearchRequest {
    private Integer queryType;
    private String orderId;
    private String merchantUserId;
}
