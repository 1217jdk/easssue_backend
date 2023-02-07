package com.limemul.easssue.mongorepo;

import com.limemul.easssue.entity.ArticleDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ArticleDocRepo extends MongoRepository<ArticleDoc,String> {

    /**
     * 기사 리스트 반환
     *  테스트용
     */
    Slice<ArticleDoc> findBy(Pageable pageable);

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
     * todo 이건 어떻게 짜야되지...ㅠㅠ 눈앞이 캄캄 ㅠㅠ
     */
}
