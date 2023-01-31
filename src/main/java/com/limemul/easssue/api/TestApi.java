package com.limemul.easssue.api;

import com.limemul.easssue.entity.ArticleDoc;
import com.limemul.easssue.entity.Test;
import com.limemul.easssue.mongorepo.ArticleDocRepo;
import com.limemul.easssue.mongorepo.TestRepo;
import com.limemul.easssue.repo.CategoryRepo;
import com.limemul.easssue.service.ArticleDocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestApi {

    private final ArticleDocService articleDocService;
    private final ArticleDocRepo articleDocRepo;
    private final CategoryRepo categoryRepo;
    private final MongoTemplate mongoTemplate;
    private final TestRepo testRepo;

    @GetMapping("/get")
    public Slice<ArticleDoc> getTest(){
        return articleDocService.getTest(0);
    }

    @GetMapping("/notin/{title}")
    public Slice<ArticleDoc> notInTest(@PathVariable String title){
        return articleDocService.notInTest(title,0);
    }

    @PostMapping("/time")
    public void timeTest(){
        ArticleDoc entity = new ArticleDoc();
        entity.setPubDate(LocalDateTime.now());
        articleDocRepo.save(entity);
    }

    @PostMapping("/notin/list")
    public Slice<Test> notInListTest(@RequestBody List<String> kwds){
        Pageable pageable= PageRequest.of(0,6);
        return testRepo.findByKwdsNotIn(Collections.singleton(kwds),pageable);
    }

//    @GetMapping("/test")
//    public Slice<ArticleDoc> getTest(){
//        return articleDocService.getPopularArticle(0);
//    }
//
//    @GetMapping("/test2")
//    public List<ArticleDoc> getTest2(){
//        return mongoTemplate.findAll(ArticleDoc.class);
//    }
//
//    @PostMapping("/test")
//    public void insertTest(){
//        Category category=categoryRepo.getReferenceById(1L);
//        log.info("category: {}",category.getName());
//        Kwd kwd = kwdRepo.findById(1L).orElseThrow();
//        log.info("kwd: {}",kwd.getName());
//        ArticleDoc articleDoc = new ArticleDoc(-1L, "test title", "test link", LocalDateTime.now(), 0, "test summary", "test img", category.getName(), kwd);
//        articleDocRepo.save(articleDoc);
//    }
}
