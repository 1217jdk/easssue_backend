package com.limemul.easssue.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private final List<String> summary=new ArrayList<>();

    private String img;

    private String category;

    private String fromKwd;

    private final List<Kwd> kwds = new ArrayList<>();

    public void updateHit() {
        this.hit++;
    }

    @Getter @Setter
    public static class Kwd {

        private String kwd;

        private int kwdCount;
    }
}
