package com.sarasa.projectboard.repository.querydsl;

import com.sarasa.projectboard.domain.Article;
import com.sarasa.projectboard.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle qArticle = QArticle.article;

        return from(qArticle)
                .distinct()
                .select(qArticle.hashtag)
                .where(qArticle.hashtag.isNotNull())
                .fetch();

        // 풀어쓴 버전
//        JPQLQuery<String> query = from(qArticle)
//                .distinct()
//                .select(qArticle.hashtag)
//                .where(qArticle.hashtag.isNotNull());
//
//        return query.fetch();
    }

}
