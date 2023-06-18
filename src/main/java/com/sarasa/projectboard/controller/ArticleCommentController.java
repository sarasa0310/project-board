package com.sarasa.projectboard.controller;

import com.sarasa.projectboard.dto.UserAccountDto;
import com.sarasa.projectboard.dto.request.ArticleCommentRequest;
import com.sarasa.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        // todo: 인증 정보 추가
        articleCommentService.saveArticleComment(
                articleCommentRequest.toDto(UserAccountDto.of(
                        "jimmy", "abcd1234", "jimmy@gmail.com", "Jimmy", "I am jimmy."
                ))
        );

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{comment-id}/delete")
    public String deleteArticleComment(@PathVariable("comment-id") Long commentId,
                                       Long articleId) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }

}
