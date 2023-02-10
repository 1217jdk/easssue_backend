package com.limemul.easssue.api.dto.dash;

import com.limemul.easssue.entity.ArticleLog;
import com.limemul.easssue.entity.ArticleLogDoc;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleDto {

    private String category;
    private String newsTitle;
    private String newsLink;

    /**
     * MySQL용 생성자
     * @param articleLog MySQL용 entity
     */
    public ArticleDto(ArticleLog articleLog) {
        this.category=articleLog.getCategory().getName();
        this.newsTitle=articleLog.getArticle().getTitle();
        this.newsLink=articleLog.getArticle().getLink();
    }

    /**
     * MongoDB용 생성자
     * @param articleLogDoc MongoDB용 entity
     */
    public ArticleDto(ArticleLogDoc articleLogDoc) {
        this.category=articleLogDoc.getCategory();
        this.newsTitle=articleLogDoc.getTitle();
        this.newsLink=articleLogDoc.getLink();
    }
}
