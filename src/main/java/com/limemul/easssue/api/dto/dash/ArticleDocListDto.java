package com.limemul.easssue.api.dto.dash;

import com.limemul.easssue.entity.ArticleLogDoc;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleDocListDto {

    private List<ArticleDto> newsList;

    public ArticleDocListDto(List<ArticleLogDoc> articleLogList) {
        this.newsList=articleLogList.stream().map(ArticleDto::new).toList();
    }
}
