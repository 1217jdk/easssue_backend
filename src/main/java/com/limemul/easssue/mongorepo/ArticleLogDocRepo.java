package com.limemul.easssue.mongorepo;

import com.limemul.easssue.entity.ArticleLogDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ArticleLogDocRepo extends MongoRepository<ArticleLogDoc, ObjectId> {

    /**
     * 해당 사용자가 해당 기사 이미 읽었는지 확인
     */
    Optional<ArticleLogDoc> findByUserIdAndArticleId(Long userId,Long articleId);
}
