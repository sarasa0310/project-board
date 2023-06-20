package com.sarasa.projectboard.controller;

import com.sarasa.projectboard.dto.request.ArticleCommentRequest;
import com.sarasa.projectboard.dto.security.BoardPrincipal;
import com.sarasa.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest,
                                        @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleCommentService.saveArticleComment(
                articleCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{comment-id}/delete")
    public String deleteArticleComment(@PathVariable("comment-id") Long commentId,
                                       Long articleId,
                                       @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleCommentService.deleteArticleComment(commentId, boardPrincipal.username());

        return "redirect:/articles/" + articleId;
    }

}
