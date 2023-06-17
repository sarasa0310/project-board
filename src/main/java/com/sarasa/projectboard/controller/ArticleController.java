package com.sarasa.projectboard.controller;

import com.sarasa.projectboard.domain.type.SearchType;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                ArticleWithCommentsResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentResponses());

        return "articles/detail";
    }

}
