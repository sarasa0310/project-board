package com.sarasa.projectboard.repository;

import com.sarasa.projectboard.domain.Article;
import com.sarasa.projectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaRepositoryTest.TestJpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository,
                             @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
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
        UserAccount userAccount = userAccountRepository.save(
                UserAccount.of("username", "password", null, null, null));

        // When
        articleRepository.save(Article.of("spring", "spring is amazing!", "#spring", userAccount));

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

    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {

        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("jimmy");
        }

    }

}
