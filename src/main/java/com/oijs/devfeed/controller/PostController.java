package com.oijs.devfeed.controller;

import com.oijs.devfeed.domain.post.dto.PostResponse;
import com.oijs.devfeed.domain.post.entity.Category;
import com.oijs.devfeed.domain.post.entity.Language;
import com.oijs.devfeed.domain.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostResponse> getPosts(@RequestParam(required = false) List<Language> language,
                                       @RequestParam(required = false) List<Category> category) {
        return postService.getPosts(language, category);
    }
}
