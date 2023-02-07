package com.limemul.easssue.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "article")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleDoc {

    @Id
    private ObjectId id;

    private Long articleId;

    private String title;

    private String link;

    private LocalDateTime pubDate;

    private int hit;

    private List<String> summary;

    private String img;

    private String category;

    private String fromKwd;

    private List<KwdDoc> kwds;

    public void updateHit() {
        this.hit++;
    }
}
