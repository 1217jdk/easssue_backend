package com.limemul.easssue.api.dto.news;

import com.limemul.easssue.api.dto.kwd.KwdDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KwdArticleListDto {

    /** 연관 키워드 리스트 */
    private List<KwdDto> kwdList;
    /** 키워드 기사 리스트 */
    private List<ArticleDto> newsList;
    private int page;
    private boolean last;
}
