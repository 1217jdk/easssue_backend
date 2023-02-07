package com.limemul.easssue.service;

import com.limemul.easssue.entity.ArticleDoc;
import com.limemul.easssue.mongorepo.ArticleDocRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArticleDocService {

    private final ArticleDocRepo articleDocRepo;

    private static final int articlesSize=6;

    public Slice<ArticleDoc> getPopularArticle(int page){
        LocalDateTime yesterday = LocalDateTime.now().minusDays(3L);
        log.info("yesterday: {}", yesterday);
        Pageable pageable= PageRequest.of(page, articlesSize);
        return articleDocRepo.findByPubDateAfterOrderByHitDesc(yesterday,pageable);
    }

}
