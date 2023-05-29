package com.sarasa.projectboard.service;

import com.sarasa.projectboard.domain.Article;
import com.sarasa.projectboard.domain.type.SearchType;
import com.sarasa.projectboard.dto.ArticleDto;
import com.sarasa.projectboard.dto.ArticleUpdateDto;
import com.sarasa.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시판 서비스 테스트")
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;
    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글 리스트 검색")
    void searchArticlesTest() {
        // Given

        // When
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "keyword");

        // Then
        assertThat(articles).isNotNull();
    }

    @Test
    @DisplayName("특정 게시글 조회")
    void findArticleTest() {
        // Given

        // When
        ArticleDto article = sut.findArticle(1L);

        // Then
        assertThat(article).isNotNull();
    }

    @Test
    @DisplayName("게시글 생성")
    void saveArticleTest() {
        // Given
        ArticleDto articleDto = ArticleDto.of(
                LocalDateTime.now(), "jimmy", "title", "content", "#spring"
        );
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // When
        sut.saveArticle(articleDto);

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글 수정")
    void updateArticleTest() {
        // Given

        // When
        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "#hashtag"));

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteArticleTest() {
        // Given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // When
        sut.deleteArticle(1L);

        // Then
        then(articleRepository).should().delete(any(Article.class));
    }

}