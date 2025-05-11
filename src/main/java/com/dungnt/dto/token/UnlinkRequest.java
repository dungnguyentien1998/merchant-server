package com.dungnt.dto.token;

import com.dungnt.dto.common.BaseRequest;

public class UnlinkRequest {
    private String orderId;
    private String merchantCustMsisdn;
    private String token;
    private String reason;
}
