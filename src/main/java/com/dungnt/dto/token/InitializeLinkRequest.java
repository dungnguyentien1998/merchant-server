package com.dungnt.dto.token;

import com.dungnt.dto.common.BaseRequest;
import com.dungnt.dto.common.TokenBaseResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitializeLinkRequest extends TokenBaseResult {
    private String paymentInstrument;
}
