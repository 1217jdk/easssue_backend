package com.limemul.easssue.mongorepo;

import com.limemul.easssue.api.dto.dash.GrassValueDocDto;
import com.limemul.easssue.entity.ArticleLogDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ArticleLogDocRepo extends MongoRepository<ArticleLogDoc, ObjectId> {

    /**
     * 해당 사용자의 카테고리별 읽은 기사 수 조회
     *  인자 clickTime 이후 읽은 기사 수
     *  카테고리 기본키 오름차순 정렬
     */
//    List<GraphValueDto> countByUserIdAndAndClickTimeAfter(Long userId, Date clickTime);

    /**
     * 해당 사용자의 날짜별 읽은 기사 수 조회
     *  인자 clickTime 이후 읽은 기사 수
     *  날짜 오름차순 정렬
     */
    @Aggregation(pipeline = {
            "{ $match: { $and: [{ 'userId': ?0}, { 'clickTime': { $gte: ?1}}]}}",
            "{ $group: { '_id': { $dateToString: { format: '%Y-%m-%d', date: '$clickTime'}}, 'count': { $sum: 1}}}",
            "{ $project: { '_id': 0, 'date': \"$_id\", 'count': 1}}"
    })
    List<GrassValueDocDto> countByUserIdAndClickTimeAfter(Long userId, Date clickTime);

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
