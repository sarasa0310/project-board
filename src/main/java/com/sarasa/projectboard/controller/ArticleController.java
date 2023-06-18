package com.sarasa.projectboard.controller;

import com.sarasa.projectboard.domain.constant.FormStatus;
import com.sarasa.projectboard.domain.type.SearchType;
import com.sarasa.projectboard.dto.UserAccountDto;
import com.sarasa.projectboard.dto.request.ArticleRequest;
import com.sarasa.projectboard.response.ArticleResponse;
import com.sarasa.projectboard.response.ArticleWithCommentsResponse;
import com.sarasa.projectboard.service.ArticleService;
import com.sarasa.projectboard.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    private final PaginationService paginationService;

    @GetMapping
    public String getArticles(@RequestParam(required = false) SearchType searchType,
                              @RequestParam(required = false) String searchValue,
                              @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                              ModelMap map) {
        Page<ArticleResponse> articleResponsePage =
                articleService.searchArticles(searchType, searchValue, pageable)
                        .map(ArticleResponse::from);

        List<Integer> barNumbers =
                paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articleResponsePage.getTotalPages());

        map.addAttribute("articles", articleResponsePage);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

        return "articles/index";
    }

    @GetMapping("/{article-id}")
    public String getOneArticle(ModelMap map,
                                @PathVariable("article-id") Long articleId) {
        ArticleWithCommentsResponse article =
                ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));

        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentResponses());

        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchArticleHashtag(@RequestParam(required = false) String searchValue,
                                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                       ModelMap map) {
        Page<ArticleResponse> articleResponsePage =
                articleService.searchArticlesViaHashtag(searchValue, pageable)
                        .map(ArticleResponse::from);

        List<Integer> barNumbers =
                paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articleResponsePage.getTotalPages());

        List<String> hashtags = articleService.getHashtags();

        map.addAttribute("articles", articleResponsePage);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchType", SearchType.HASHTAG);
        map.addAttribute("hashtags", hashtags);

        return "articles/search-hashtag";
    }

    @GetMapping("/form")
    public String articleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }

    @PostMapping("/form")
    public String postNewArticle(ArticleRequest articleRequest) {
        articleService.saveArticle(articleRequest.toDto(
                UserAccountDto.of(
                        "jimmy", "abcd1234", "jimmy@gmail.com", "jimmy", "memo"
                )
        ));

        return "redirect:/articles";
    }

    @GetMapping("/{article-id}/form")
    public String updateArticleForm(@PathVariable("article-id") Long articleId, ModelMap map) {
        ArticleResponse article =
                ArticleResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/form";
    }

    @PostMapping("/{article-id}/form")
    public String updateArticle(@PathVariable("article-id") Long articleId, ArticleRequest articleRequest) {
        // todo: 인증 정보 추가
        articleService.updateArticle(articleId, articleRequest.toDto(
                UserAccountDto.of(
                        "jimmy", "abcd1234", "jimmy@gmail.com", "jimmy", "memo"
                )
        ));

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/{article-id}/delete")
    public String deleteArticle(@PathVariable("article-id") Long articleId) {
        // todo: 인증 정보 추가
        articleService.deleteArticle(articleId);

        return "redirect:/articles";
    }

}
