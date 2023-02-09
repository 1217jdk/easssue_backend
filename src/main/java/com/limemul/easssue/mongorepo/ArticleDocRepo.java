package com.limemul.easssue.mongorepo;

import com.limemul.easssue.entity.ArticleDoc;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ArticleDocRepo extends MongoRepository<ArticleDoc, ObjectId> {

    /**
     * 기사 리스트 반환
     *  테스트용
     */
    Slice<ArticleDoc> findBy(Pageable pageable);

    /**
     * articleId에 해당하는 기사 반환
     */
    Optional<ArticleDoc> findByArticleId(Long articleId);

    /**
     * 기사 리스트 반환
     *  pubDate 이후 올라온 기사
     *  조회수 내림차순 정렬
     */
    Slice<ArticleDoc> findByPubDateAfterOrderByHitDesc(LocalDateTime pubDate, Pageable pageable);

    /**
     * 해당 사용자의 금지 키워드 포함하는 기사 제외한 기사 리스트 반환
     *  pubDate 이후 올라온 기사
     *  조회수 내림차순 정렬
     */
    @Query("{'pubDate': {$gt: ?0},'kwds.kwd': {$nin: ?1}}")
    Slice<ArticleDoc> findByPubDateAfterAndKwdsNotInOrderByHitDesc(Date pubDate,
                                                                   List<String> kwds,
                                                                   Pageable pageable);
}
