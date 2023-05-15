package com.sarasa.projectboard.repository;

import com.sarasa.projectboard.config.JpaConfig;
import com.sarasa.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
    @DisplayName("SELECT 테스트")
    void testSelect() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @Test
    @DisplayName("INSERT 테스트")
    void testInsert() {
        // Given
        long previousArticleCount = articleRepository.count();

        // When
        articleRepository.save(Article.of("spring", "spring is amazing!", "#spring"));

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount + 1);
    }

    @Test
    @DisplayName("UPDATE 테스트")
    void testUpdate() {
        // Given
        Article updatedArticle = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#spring";
        updatedArticle.setHashtag(updatedHashtag);

        // When
        articleRepository.saveAndFlush(updatedArticle);

        // Then
        assertThat(updatedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @Test
    @DisplayName("DELETE 테스트")
    void testDelete() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();

        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();

        int deletedArticleCommentSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);

        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedArticleCommentSize);
    }

}
