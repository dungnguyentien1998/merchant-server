package com.dungnt.dto.common;

import lombok.Data;

import java.util.Date;

@Data
public class TokenBaseResult extends BaseRequest {
    private String merchantCustMsisdn;
    private String merchantUserId;
    private String provider;
    private Integer transactionStatus;
    private String errorCode;
    private Long dailyLimit;
    private Long transLimit;
    private Date linkDate;
    private String token;
}
