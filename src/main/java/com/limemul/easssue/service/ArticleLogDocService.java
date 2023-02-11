package com.limemul.easssue.service;

import com.limemul.easssue.api.dto.dash.GrassValueDocDto;
import com.limemul.easssue.entity.ArticleDoc;
import com.limemul.easssue.entity.ArticleLogDoc;
import com.limemul.easssue.entity.User;
import com.limemul.easssue.mongorepo.ArticleDocRepo;
import com.limemul.easssue.mongorepo.ArticleLogDocRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArticleLogDocService {

    private final ArticleLogDocRepo articleLogDocRepo;
    private final ArticleDocRepo articleDocRepo;

    /**
     * 방사형 그래프 정보 조회
     *  해당 유저의 한 주간 카테고리별 읽은 기사 수 반환
     */
//    public List<GraphValueDto> getRadialGraphInfo(User user){
//        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1L);
//        return articleLogDocRepo.countByUserAndClickTimeAfterGroupByCategory(user, lastWeek);
//    }

    /**
     * 캘린더 히트맵 정보 조회
     * 해당 유저의 이번 한 달 날짜별 읽은 기사 수 반환
     */
    public List<GrassValueDocDto> getCalendarHeatMapInfo(User user){
        LocalDateTime firstDayOfMonth = LocalDate.now().atStartOfDay().with(firstDayOfMonth());
        log.info("firstDayOfMonth: {}",firstDayOfMonth);

        //LocalDateTime 타입을 Date 타입으로 변경
        Date clickTime = Date.from(firstDayOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        log.info("query clickTime: {}",clickTime);

        return articleLogDocRepo.countByUserIdAndClickTimeAfter(user.getId(), clickTime);
    }

    /**
     * 읽은 기사 리스트 조회
     *  해당 유저가 해당 날짜에 읽은 기사 정보 리스트 반환
     */
    public List<ArticleLogDoc> getArticleLogByReadDate(User user, LocalDate date){
        LocalDateTime clickTime = LocalDateTime.of(date, LocalTime.MIN);
        log.info("query clickTime: {}",clickTime);
        return articleLogDocRepo.findByUserIdAndClickTimeAfter(user.getId(), clickTime);
    }

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
            return articleLogDocRepo.save(ArticleLogDoc.of(user,articleDoc));
        }else{
            return optionalArticleLogDoc.get();
        }
    }
}
