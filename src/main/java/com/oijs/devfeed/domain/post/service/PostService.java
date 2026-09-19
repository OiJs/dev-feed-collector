package com.oijs.devfeed.domain.post.service;

import com.oijs.devfeed.domain.post.dto.PostResponse;
import com.oijs.devfeed.domain.post.entity.Category;
import com.oijs.devfeed.domain.post.entity.Language;
import com.oijs.devfeed.domain.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponse> getPosts(List<Language> language, List<Category> categories) {
        return postRepository.findFiltered(language, categories)
                .stream()
                .map(PostResponse::from)
                .toList();
    }
}
