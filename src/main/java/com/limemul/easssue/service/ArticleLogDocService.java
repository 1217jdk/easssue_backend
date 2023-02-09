package com.limemul.easssue.service;

import com.limemul.easssue.entity.*;
import com.limemul.easssue.mongorepo.ArticleDocRepo;
import com.limemul.easssue.mongorepo.ArticleLogDocRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleLogDocService {

    private final ArticleLogDocRepo articleLogDocRepo;
    private final ArticleDocRepo articleDocRepo;

    /**
     * 읽은 기사 로그 남기기
     *  현재 시간에 해당 유저가 해당 기사 읽었다는 로그 저장
     *  (같은 기사 여러번 안 들어가게)
     */
    @Transactional
    public ArticleLogDoc logReadArticle(User user, Long articleId){
        ArticleDoc articleDoc = articleDocRepo.findByArticleId(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기사입니다."));

        Optional<ArticleLogDoc> optionalArticleLogDoc = articleLogDocRepo
                .findByUserIdAndArticleId(user.getId(), articleId);
        if(optionalArticleLogDoc.isEmpty()) {
            return articleLogDocRepo.save(ArticleLogDoc.of(user.getId(), articleId, articleDoc.getCategory()));
        }else{
            return optionalArticleLogDoc.get();
        }
    }
}
