package com.limemul.easssue.mongorepo;

import com.limemul.easssue.entity.ArticleDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ArticleDocRepo extends MongoRepository<ArticleDoc,String> {

    Slice<ArticleDoc> findByOrderByPubDateDesc(Pageable pageable);
    Slice<ArticleDoc> findByTitleNotIn(Collection<String> title, Pageable pageable);
    Slice<ArticleDoc> findByPubDateAfterOrderByHitDesc(LocalDateTime pubDate, Pageable pageable);
}
