package com.limemul.easssue.api.dto.news;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleDocListDto {

    private List<ArticleDto> newsList;
    private int page;
    private boolean last;

    public ArticleDocListDto(List<ArticleDto> newsList, int page, boolean last) {
        this.newsList = newsList;
        this.page = page;
        this.last = last;
    }
}
