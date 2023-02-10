package com.limemul.easssue.api.dto.dash;

import com.limemul.easssue.entity.ArticleLog;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleListDto {

    private List<ArticleDto> newsList;

    public ArticleListDto(List<ArticleLog> articleLogList) {
        this.newsList=articleLogList.stream().map(ArticleDto::new).toList();
    }
}
