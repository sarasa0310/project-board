package com.sarasa.projectboard.service;

import com.sarasa.projectboard.domain.Article;
import com.sarasa.projectboard.domain.ArticleComment;
import com.sarasa.projectboard.domain.type.SearchType;
import com.sarasa.projectboard.dto.ArticleCommentDto;
import com.sarasa.projectboard.dto.ArticleDto;
import com.sarasa.projectboard.repository.ArticleCommentRepository;
import com.sarasa.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("댓글 서비스 테스트")
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService sut;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @Test
    @DisplayName("게시글 ID로 조회 시 댓글 리스트 조회")
    void getArticleCommentsTest() {
        // Given
        Long articleId = 1L;
        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(Article.of("title", "content", "#java")));

        // When
        List<ArticleCommentDto> articleComments = sut.getArticleComments(1L);

        // Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

//    @Test
//    @DisplayName("댓글 생성")
//    void saveArticleCommentTest() {
//        // Given
//        ArticleCommentDto articleCommentDto = ArticleCommentDto.of(
//                LocalDateTime.now(), "dabin", "love"
//        );
//        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
//
//        // When
//        sut.saveArticleComment(articleCommentDto);
//
//        // Then
//        then(articleCommentRepository).should().save(any(ArticleComment.class));
//    }

}