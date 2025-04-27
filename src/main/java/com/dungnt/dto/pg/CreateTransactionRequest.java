package com.dungnt.dto.pg;

import com.dungnt.dto.common.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest extends BaseRequest {
    private String transAmount;
    private String description;
    private String otherInfo;
}
