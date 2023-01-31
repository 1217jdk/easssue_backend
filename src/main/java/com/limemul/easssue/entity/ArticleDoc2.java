package com.limemul.easssue.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "article")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleDoc2 {

    @Id
    private ObjectId id;

    private Long articleId;

    private String title;

    private String link;

    private LocalDateTime pubDate;

    private int hit;

    private String summary;

    private String img;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private Category category;
    private String category;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "from_kwd_id")
    private String fromKwd;

    //    @OneToMany(mappedBy = "article")
//    private final List<ArticleKwd> articleKwds = new ArrayList<>();
//    private List<String> articleKwds = new ArrayList<>();
    private List<Integer> articleKwds = new ArrayList<>();

    public void updateHit() {
        this.hit++;
    }
}
