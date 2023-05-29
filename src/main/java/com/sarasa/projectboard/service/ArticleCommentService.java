package com.sarasa.projectboard.service;

import com.sarasa.projectboard.dto.ArticleCommentDto;
import com.sarasa.projectboard.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> getArticleComments(Long id) {
        return List.of();
    }

}
