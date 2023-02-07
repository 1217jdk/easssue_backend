package com.limemul.easssue.api;

import com.limemul.easssue.api.dto.news.ArticleDocListDto;
import com.limemul.easssue.api.dto.news.ArticleDto;
import com.limemul.easssue.entity.ArticleDoc;
import com.limemul.easssue.entity.Test;
import com.limemul.easssue.mongorepo.ArticleDocRepo;
import com.limemul.easssue.mongorepo.TestRepo;
import com.limemul.easssue.service.ArticleDocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestApi {

    private final ArticleDocService articleDocService;
    private final ArticleDocRepo articleDocRepo;
    private final TestRepo testRepo;

    @GetMapping("/datetime/{min}")
    public Slice<Test> dateTimeTest(@PathVariable Long min){
        LocalDateTime pubDate = LocalDateTime.now().minusMinutes(min);
        PageRequest of = PageRequest.of(0, 6);
        return testRepo.findByPubDateAfter(pubDate, of);
    }

    @GetMapping("/news")
    public ArticleDocListDto allNews(){
        int page = 0;
        Pageable pageable=PageRequest.of(page,6);
        Slice<ArticleDoc> newsSlice = articleDocRepo.findBy(pageable);
        List<ArticleDto> newsList = newsSlice.stream().map(ArticleDto::new).toList();
        return new ArticleDocListDto(newsList,page,newsSlice.isLast());
    }

    @GetMapping("/news/popular/{hour}")
    public ArticleDocListDto popularNewsV3_0(@PathVariable Long hour){
        LocalDateTime pubDate = LocalDateTime.now().minusHours(hour);
        int page = 0;
        Pageable pageable=PageRequest.of(page,6);
        Slice<ArticleDoc> newsSlice = articleDocRepo.findByPubDateAfterOrderByHitDesc(pubDate, pageable);
        List<ArticleDto> newsList = newsSlice.stream().map(ArticleDto::new).toList();
        return new ArticleDocListDto(newsList,page,newsSlice.isLast());
    }

}
