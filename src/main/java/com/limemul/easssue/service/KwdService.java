package com.limemul.easssue.service;

import com.limemul.easssue.entity.Kwd;
import com.limemul.easssue.repo.KwdRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KwdService {

    private final KwdRepo kwdRepo;

    /**
     * 기본키로 키워드 조회
     */
    public Kwd getKwdById(Long id){
        Optional<Kwd> optionalKwd = kwdRepo.findById(id);
        //없는 키워드인 경우 예외 발생
        if (optionalKwd.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 키워드입니다.");
        }
        return optionalKwd.get();
    }

    /**
     * 랜덤 키워드 하나 조회
     *  최근 일주일 기사에 있는 키워드 중에서
     */
    public List<Kwd> getRandomKwd(){
        return kwdRepo.findRandomOrderByArticleKwd();
    }

    /**
     * 검색한 키워드 리스트 조회
     */
    public List<Kwd> getSearchKwdList(String searchStr){
        return kwdRepo.findByNameContains(searchStr);
    }

    /**
     * 연관 키워드 리스트 조회
     *   최신 5개
     *   등록일 내림차순 정렬
     */
    public List<Kwd> getRelKwdList(Kwd fromKwd){
        Pageable pageable= PageRequest.of(0,5, Sort.by("regDate").descending());
        return kwdRepo.findDistinctByFromKwd(fromKwd,pageable);
    }
}
