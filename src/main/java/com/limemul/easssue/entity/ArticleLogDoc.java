package com.limemul.easssue.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document(collection = "articleLog")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLogDoc {

    @Id
    private ObjectId id;

    private LocalDateTime clickTime;

    private Long userId;

    private Long articleId;

    private String category;

    private ArticleLogDoc(Long userId, Long articleId, String category) {
        this.clickTime=LocalDateTime.now();
        this.userId = userId;
        this.articleId = articleId;
        this.category = category;
    }

    public static ArticleLogDoc of(Long userId, Long articleId, String category){
        return new ArticleLogDoc(userId,articleId,category);
    }
}
