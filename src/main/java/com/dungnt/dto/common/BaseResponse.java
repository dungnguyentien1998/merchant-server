package com.dungnt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public class BaseResponse {
    private String url;
    private String qrCode;
    private String deepLink;
}
