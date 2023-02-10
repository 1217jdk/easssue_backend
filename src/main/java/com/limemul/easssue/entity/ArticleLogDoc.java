package com.limemul.easssue.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

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

    private String title;

    private String link;

    private ArticleLogDoc(User user,ArticleDoc articleDoc) {
        this.clickTime=LocalDateTime.now();
        this.userId = user.getId();
        this.articleId = articleDoc.getArticleId();
        this.category = articleDoc.getCategory();
        this.title = articleDoc.getTitle();
        this.link=articleDoc.getLink();
    }

    public static ArticleLogDoc of(User user,ArticleDoc articleDoc){
        return new ArticleLogDoc(user,articleDoc);
    }
}
