package com.oijs.devfeed.domain.post.dto;

import com.oijs.devfeed.domain.post.entity.Post;
import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String link,
        String source,
        String summary,
        String language,
        String category,
        LocalDateTime publishedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getLink(),
                post.getSource(),
                post.getSummary(),
                post.getLanguage(),
                post.getCategory(),
                post.getPublishedAt()
        );
    }
}