package com.limemul.easssue.api;

import com.limemul.easssue.api.dto.dash.GraphDocDto;
import com.limemul.easssue.api.dto.dash.GraphValueDocDto;
import com.limemul.easssue.api.dto.dash.GrassDocDto;
import com.limemul.easssue.api.dto.news.ArticleDto;
import com.limemul.easssue.api.dto.news.ArticleListDto;
import com.limemul.easssue.entity.ArticleDoc;
import com.limemul.easssue.entity.Category;
import com.limemul.easssue.entity.Test;
import com.limemul.easssue.entity.User;
import com.limemul.easssue.jwt.JwtProvider;
import com.limemul.easssue.mongorepo.ArticleDocRepo;
import com.limemul.easssue.mongorepo.TestRepo;
import com.limemul.easssue.service.ArticleLogDocService;
import com.limemul.easssue.service.CategoryService;
import com.limemul.easssue.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestApi {

    private final ArticleDocRepo articleDocRepo;
    private final TestRepo testRepo;
    private final ArticleLogDocService articleLogDocService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/datetime/{min}")
    public Slice<Test> dateTimeTest(@PathVariable Long min){
        LocalDateTime pubDate = LocalDateTime.now().minusMinutes(min);
        PageRequest of = PageRequest.of(0, 6);
        return testRepo.findByPubDateAfter(pubDate, of);
    }

    @GetMapping("/news")
    public ArticleListDto allNews(){
        int page = 0;
        Pageable pageable=PageRequest.of(page,6);
        Slice<ArticleDoc> newsSlice = articleDocRepo.findBy(pageable);
        List<ArticleDto> newsList = newsSlice.stream().map(ArticleDto::new).toList();
        return new ArticleListDto(newsList,page,newsSlice.isLast());
    }

    @GetMapping("/news/popular/{hour}")
    public ArticleListDto popularNewsV3(@PathVariable Long hour){
        LocalDateTime pubDate = LocalDateTime.now().minusHours(hour);
        int page = 0;
        Pageable pageable=PageRequest.of(page,6);
        Slice<ArticleDoc> newsSlice = articleDocRepo.findByPubDateAfterOrderByHitDesc(pubDate, pageable);
        List<ArticleDto> newsList = newsSlice.stream().map(ArticleDto::new).toList();
        return new ArticleListDto(newsList,page,newsSlice.isLast());
    }

    @GetMapping("/dash/graph")
    public GraphDocDto dashGraph(@RequestHeader HttpHeaders headers){
        //사용자 정보 불러오기
        Optional<User> optionalUser = JwtProvider.getUserFromJwt(userService, headers);

        //로그인 안하면 예외 발생
        if(optionalUser.isEmpty()){
            throw new NoSuchElementException("로그인 후 사용할 수 있는 기능입니다.");
        }

        User user = optionalUser.get();

        //방사형 그래프 (최근 일주일)
        List<Category> categories=categoryService.getAllCategories();
        List<GraphValueDocDto> radialGraphInfo = articleLogDocService.getRadialGraphInfo(user);

        return new GraphDocDto(categories,radialGraphInfo);
    }

    @GetMapping("/dash/grass")
    public GrassDocDto dashGrass(@RequestHeader HttpHeaders headers){
        //사용자 정보 불러오기
        Optional<User> optionalUser = JwtProvider.getUserFromJwt(userService, headers);

        //로그인 안하면 예외 발생
        if(optionalUser.isEmpty()){
            throw new NoSuchElementException("로그인 후 사용할 수 있는 기능입니다.");
        }

        User user = optionalUser.get();
        return new GrassDocDto(articleLogDocService.getCalendarHeatMapInfo(user));
    }
}
