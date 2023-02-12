package com.limemul.easssue.api.dto.news;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleListDto {

    /** 인기/추천/연관 기사 리스트 */
    private List<ArticleDto> newsList;
    private int page;
    private boolean last;
}
