package com.sarasa.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping
    public String getArticles(ModelMap map) {
        map.addAttribute("articles", List.of());

        return "articles/index";
    }

    @GetMapping("/{article-id}")
    public String getOneArticle(ModelMap map,
                                @PathVariable("article-id") Long articleId) {
        map.addAttribute("article", "articleValue"); // todo: 실제 데이터로 교체 필요
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }

}
