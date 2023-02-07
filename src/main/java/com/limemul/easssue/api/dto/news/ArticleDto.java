package com.limemul.easssue.api.dto.news;

import com.limemul.easssue.entity.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
public class ArticleDto {

    private Long newsId;
    private String title;
    private String link;
    private LocalDateTime pubDate;
    private List<String> summary;
    private String img;
    private List<String> keywords;

    /**
     * MySQL용 생성자
     * @param article MySQL용 entity
     */
    public ArticleDto(Article article){
        newsId = article.getId();
        title = article.getTitle();
        link = article.getLink();
        //서버 시간과 DB 시간이 9시간 차이나는 문제
        pubDate = article.getPubDate().plusHours(9L);
        summary = Arrays.stream(article.getSummary().split("\n")).toList();
        img = article.getImg();
        //빈도 높은 키워드 3개 반환
        keywords = article.getArticleKwds().stream().sorted((o1, o2) -> o2.getCount() - o1.getCount())
                .limit(3).map(ArticleKwd::getKwd).map(Kwd::getName).toList();
    }

    /**
     * MongoDB용 생성자
     * @param articleDoc MongoDB용 entity
     */
    public ArticleDto(ArticleDoc articleDoc) {
        newsId=articleDoc.getArticleId();
        title=articleDoc.getTitle();
        link=articleDoc.getLink();
        pubDate=articleDoc.getPubDate();
        summary=articleDoc.getSummary();
        img=articleDoc.getImg();
        //빈도 높은 키워드 3개 반환
        keywords=articleDoc.getKwds().stream().sorted((o1, o2) -> o2.getKwdCount()-o1.getKwdCount())
                .limit(3).map(KwdDoc::getKwd).toList();
    }
}
