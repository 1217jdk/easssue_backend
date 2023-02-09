package com.limemul.easssue.service;

import com.limemul.easssue.api.dto.news.ArticleDocListDto;
import com.limemul.easssue.api.dto.news.ArticleDto;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArticleDocService {

    private final ArticleDocRepo articleDocRepo;

    private static final int articlesSize=6;

    /**
     * 인기 기사 리스트 조회
     *  최근 하루동안 올라온 기사
     *  조회수 내림차순 정렬
     */
    public ArticleDocListDto getPopularArticle(int page){
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1L);
        log.info("yesterday: {}", yesterday);
        Pageable pageable= PageRequest.of(page, articlesSize);

        Slice<ArticleDoc> articleDocs = articleDocRepo.findByPubDateAfterOrderByHitDesc(yesterday, pageable);
        List<ArticleDto> articleDtos = articleDocs.stream().map(ArticleDto::new).toList();

        return new ArticleDocListDto(articleDtos,page,articleDocs.isLast());
    }

    /**
     * 금지 키워드 제외한 인기 기사 리스트 조회
     *  최근 하루동안 올라온 기사
     *  조회수 내림차순 정렬
     *  해당 사용자의 금지 키워드 포함하는 기사 제외한 기사 리스트 반환
     */
    public ArticleDocListDto getPopularArticleExcludeBanKwd(List<String> banKwds,int page){
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1L);
        log.info("yesterday: {}", yesterday);
        Pageable pageable= PageRequest.of(page, articlesSize);

        //LocalDateTime 타입을 Date 타입으로 변경
        Date pubDate = Date.from(yesterday.atZone(ZoneId.systemDefault()).toInstant());
        log.info("query pubDate: {}",pubDate);
        Slice<ArticleDoc> articleDocs = articleDocRepo
                .findByPubDateAfterAndKwdsNotInOrderByHitDesc(pubDate, banKwds, pageable);
        List<ArticleDto> articleDtos = articleDocs.stream().map(ArticleDto::new).toList();

        return new ArticleDocListDto(articleDtos,page,articleDocs.isLast());
    }

    /**
     * 기사 조회수 올리기
     */
    @Transactional
    public void updateHit(Long articleId) {
        Optional<ArticleDoc> optionalArticleDoc = articleDocRepo.findByArticleId(articleId);
        if(optionalArticleDoc.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 기사입니다.");
        }

        ArticleDoc articleDoc = optionalArticleDoc.get();

        articleDoc.updateHit();
        articleDocRepo.save(articleDoc);
    }
}
