package com.sarasa.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "username")
    private UserAccount userAccount;

    @Setter
    @ManyToOne(optional = false)
    private Article article;

    protected ArticleComment() {
    }

    private ArticleComment(String content, UserAccount userAccount, Article article) {
        this.content = content;
        this.userAccount = userAccount;
        this.article = article;
    }

    public static ArticleComment of(String content, UserAccount userAccount, Article article) {
        return new ArticleComment(content, userAccount, article);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
