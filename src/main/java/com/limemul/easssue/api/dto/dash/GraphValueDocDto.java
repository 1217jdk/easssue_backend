package com.limemul.easssue.api.dto.dash;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GraphValueDocDto {

    /** 카테고리명 */
    private String category;
    /** 카테고리별 읽은 기사 수 */
    private int count;
}
