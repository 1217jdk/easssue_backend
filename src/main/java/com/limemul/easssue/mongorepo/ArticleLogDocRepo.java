package com.limemul.easssue.mongorepo;

import com.limemul.easssue.entity.ArticleLogDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleLogDocRepo extends MongoRepository<ArticleLogDoc, ObjectId> {

    /**
     * 해당 사용자의 해당 날짜에 읽은 기사 정보 조회
     *  인자 clickTime 이후 읽은 기사 정보
     */
    List<ArticleLogDoc> findByUserIdAndClickTimeAfter(Long userId, LocalDateTime clickTime);

    /**
     * 해당 사용자가 해당 기사 이미 읽었는지 확인
     */
    Optional<ArticleLogDoc> findByUserIdAndArticleId(Long userId,Long articleId);
}
