package com.limemul.easssue.api;

import com.limemul.easssue.api.dto.news.ArticleDocListDto;
import com.limemul.easssue.api.dto.news.ArticleListDto;
import com.limemul.easssue.api.dto.news.KwdArticleDto;
import com.limemul.easssue.entity.*;
import com.limemul.easssue.jwt.JwtProvider;
import com.limemul.easssue.repo.KwdRepo;
import com.limemul.easssue.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Slf4j
public class NewsApi {

    private final ArticleService articleService;
    private final ArticleLogService articleLogService;
    private final ArticleDocService articleDocService;
    private final ArticleLogDocService articleLogDocService;
    private final UserService userService;
    private final UserKwdService userKwdService;
    private final KwdRepo kwdRepo;

    /**
     * 인기 기사 리스트 반환 api
     * 조건: page >= 0
     */
    @GetMapping("/popular/page/{page}")
    public ArticleListDto popularNews(@PathVariable Integer page){
        log.info("[Starting request] GET /news/popular/page/{}", page);
        ArticleListDto result = articleService.getPopularArticle(page);
        log.info("[Finished request] GET /news/popular/page/{}", page);
        return result;
    }

    /**
     * 인기 기사 v2 - MySQL
     *  오늘의 인기 기사 리스트 조회
     *  [로그인 o] 금지 키워드 들어있는 기사 제외한 인기 기사 반환
     *  [로그인 x] v1 (GET /news/popular/page/{page}) 과 동일
     */
    @GetMapping("/popular/v2/page/{page}")
    public ArticleListDto popularNewsV2(@RequestHeader HttpHeaders headers,@PathVariable int page){
        log.info("[Starting request] GET /news/popular/v2/page/{}", page);

        //사용자 정보 불러오기
        Optional<User> optionalUser = JwtProvider.getUserFromJwt(userService, headers);

        //로그인 안하면 오늘의 인기 기사 리스트 반환
        if(optionalUser.isEmpty()){
            log.info("User not signed in");
            log.info("[Finished request] GET /news/popular/v2/page/{}",page);
            return articleService.getPopularArticle(page);
        }

        //로그인 했으면 금지 키워드 들어있는 기사 제외하고 반환
        User user = optionalUser.get();
        log.info("userId: {}",user.getId());
        log.info("[Finished request] GET /news/popular/v2/page/{}", page);
        return articleService.getPopularArticleExcludeBanKwd(user,page);
    }

    /**
     * 인기 기사 v3 - MongoDB
     *  오늘의 인기 기사 리스트 조회
     *  [로그인 o] 금지 키워드 들어있는 기사 제외한 인기 기사 반환
     *  [로그인 x] 최근 하루동안 올라온 인기 기사 반환
     */
    @GetMapping("/popular/v3/page/{page}")
    public ArticleDocListDto popularNewsV3(@RequestHeader HttpHeaders headers, @PathVariable int page){
        log.info("[Starting request] GET /news/popular/v3/page/{}", page);

        //사용자 정보 불러오기
        Optional<User> optionalUser = JwtProvider.getUserFromJwt(userService, headers);

        //로그인 안하면 오늘의 인기 기사 리스트 반환
        if(optionalUser.isEmpty()){
            log.info("User not signed in");
            log.info("[Finished request] GET /news/popular/v3/page/{}",page);
            return articleDocService.getPopularArticle(page);
        }

        //로그인 했으면 금지 키워드 들어있는 기사 제외하고 반환
        User user = optionalUser.get();
        log.info("userId: {}",user.getId());

        //해당 유저의 금지 키워드
        List<String> banKwds = userKwdService.getUserKwdList(user, UserKwdType.b)
                .stream().map(UserKwd::getKwd)
                .map(Kwd::getName)
                .toList();
        log.info("user ban kwds: {}",banKwds);

        log.info("[Finished request] GET /news/popular/v3/page/{}", page);
        return articleDocService.getPopularArticleExcludeBanKwd(banKwds,page);
    }

    /**
     * 구독 키워드 기사 리스트 반환 api
     */
    @GetMapping("/subscribe/{kwdId}/page/{page}")
    public KwdArticleDto kwdNews(@RequestHeader HttpHeaders headers, @PathVariable Long kwdId, @PathVariable Integer page){
        log.info("[Starting request] GET /news/subscribe/{}/page/{}", kwdId, page);
        Optional<Kwd> targetKwd = kwdRepo.findById(kwdId);
        if (targetKwd.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 키워드입니다.");
        }

        Optional<User> user = JwtProvider.getUserFromJwt(userService, headers);
        if (user.isPresent()){
            log.info("user id is {}", user.get().getId());

            List<UserKwd> userKwdList = userKwdService.getUserKwdList(user.get(), UserKwdType.s);
            boolean flag = false;
            for( UserKwd userKwd : userKwdList) {
                if (targetKwd.get().equals(userKwd.getKwd())) {
                    flag = true;
                    break;
                }
            }
            log.info("flag is {}", flag);
            if ( !flag ) {
                throw new IllegalArgumentException("유저가 구독하지 않은 키워드입니다.");
            }
        }

        KwdArticleDto result = articleService.getSubsArticle(targetKwd.get(), page);

        log.info("[Finished request] GET /news/subscribe/{}/page/{}", kwdId, page);
        return result;

    }

    /**
     * 선택 키워드의 기사리스트 반환 api
     * 단, 연관키워드 또는 추천키워드 일 경우
     */
    @GetMapping("/recommend/{kwdId}/page/{page}")
    public ArticleListDto recommendNews(@RequestHeader HttpHeaders headers, @PathVariable Long kwdId, @PathVariable Integer page){
        log.info("[Starting request] GET /news/recommend/{}/page/{}", kwdId, page);

        Optional<Kwd> targetKwd = kwdRepo.findById(kwdId);

        if(targetKwd.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 키워드입니다.");
        }

        ArticleListDto result = articleService.getRecommendedArticle(targetKwd.get(), page);

        log.info("[Finished request] GET /news/recommend/{}/page/{}", kwdId, page);
        return result;
    }


    /**
     * 기사 로그 남기기 - MySQL
     *  [로그인 o] 해당 사용자가 언제 무슨 카테고리의 무슨 기사 읽었는지 로그 남기고, 조회수 올리기
     *  [로그인 x] 조회수만 올리기
     */
    @PostMapping("/log/{articleId}")
    public boolean logReadArticle(@RequestHeader HttpHeaders headers,@PathVariable Long articleId){
        log.info("[Starting request] POST /news/log/{}",articleId);

        //사용자 정보 불러오기
        Optional<User> optionalUser = JwtProvider.getUserFromJwt(userService, headers);

        //조회수 올리기
        articleService.updateHit(articleId);

        //로그인 안하면 아무 작업 안함
        if(optionalUser.isEmpty()){
            return false;
        }

        //기사 로그 남기기
        User user = optionalUser.get();
        ArticleLog articleLog = articleLogService.logReadArticle(user, articleId);
        log.info("userId: {}, articleId: {}, clickTime: {}",user.getId(),articleId,articleLog.getClickTime());

        log.info("[Finished request] POST /news/log/{}",articleId);
        return true;
    }

    /**
     * 기사 로그 남기기 v2 - MongoDB
     *  [로그인 o] 해당 사용자가 언제 무슨 카테고리의 무슨 기사 읽었는지 로그 남기고, 조회수 올리기
     *  [로그인 x] 조회수만 올리기
     */
    @PostMapping("/log/v2/{articleId}")
    public boolean logReadArticleV2(@RequestHeader HttpHeaders headers,@PathVariable Long articleId){
        log.info("[Starting request] POST /news/log/v2/{}",articleId);

        //사용자 정보 불러오기
        Optional<User> optionalUser = JwtProvider.getUserFromJwt(userService, headers);

        //조회수 올리기
        articleDocService.updateHit(articleId);

        //로그인 안하면 아무 작업 안함
        if(optionalUser.isEmpty()){
            log.info("[Finished request] POST /news/log/v2/{}",articleId);
            return false;
        }

        //기사 로그 남기기
        User user = optionalUser.get();

        ArticleLogDoc articleLogDoc = articleLogDocService.logReadArticle(user, articleId);
        log.info("userId: {}, articleId: {}, clickTime: {}",user.getId(),articleId,articleLogDoc.getClickTime());

        log.info("[Finished request] POST /news/log/v2/{}",articleId);
        return true;
    }
}
