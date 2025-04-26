package com.dungnt.dto.token;

import com.dungnt.dto.common.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitializeLinkRequest extends BaseRequest {
    private String merchantCustMsisdn;
    private String merchantUserId;
    private String provider;
    private String paymentInstrument;
    private Long dailyLimit;
    private Long transLimit;
}
