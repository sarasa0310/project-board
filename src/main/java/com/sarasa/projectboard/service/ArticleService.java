package com.sarasa.projectboard.service;

import com.sarasa.projectboard.domain.type.SearchType;
import com.sarasa.projectboard.dto.ArticleDto;
import com.sarasa.projectboard.dto.ArticleWithCommentsDto;
import com.sarasa.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String keyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto articleDto) {
    }

    public void updateArticle(ArticleDto articleDto) {
    }

    public void deleteArticle(Long articleId) {
    }

}
