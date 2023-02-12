package com.limemul.easssue.api.dto.dash;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GrassValueDocDto {

    private String date;
    private int count;
}
