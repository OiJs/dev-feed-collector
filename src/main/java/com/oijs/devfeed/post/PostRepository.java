package com.oijs.devfeed.post;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByLink(String link);

    @Query("""
            select p from Post p
            where(:language is null or p.language in :language)
            and(:categories is null or p.category in :categories)
            order by p.publishedAt desc
            """)
    List<Post> findFiltered(@Param("language") List<Language> language,
                            @Param("categories") List<Category> categories);
}
