package com.sarasa.projectboard.service;

import com.sarasa.projectboard.domain.type.SearchType;
import com.sarasa.projectboard.dto.ArticleDto;
import com.sarasa.projectboard.dto.ArticleUpdateDto;
import com.sarasa.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String keyword) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto findArticle(Long id) {
        return ArticleDto.of(LocalDateTime.now(), "jimmy", "study spring", "fun", "spring");
    }

    public void saveArticle(ArticleDto articleDto) {

    }

    public void updateArticle(Long id, ArticleUpdateDto articleUpdateDto) {

    }

    public void deleteArticle(Long id) {

    }

}
