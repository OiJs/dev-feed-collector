package com.oijs.devfeed.feed;

import com.oijs.devfeed.post.Category;
import com.oijs.devfeed.post.Language;
import com.oijs.devfeed.post.Post;
import com.oijs.devfeed.post.PostRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedFetchScheduler {

    private final FeedProperties feedProperties;
    private final PostRepository postRepository;
    private final PostClassifierService postClassifierService;

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES, initialDelay = 0)
    public void fetchAll() {
        for(FeedSource source : feedProperties.sources()) {
            fetchOne(source);
        }
    }

    private void fetchOne(FeedSource source) {
        try {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(URI.create(source.url()).toURL()));

            for(SyndEntry entry : feed.getEntries()) {
                saveIfNew(source, entry);
            }
        } catch (Exception e) {
            log.warn("피드 수집 실패: {} ({})", source.name(), e.getMessage());
        }
    }

    private void saveIfNew(FeedSource source, SyndEntry entry) {
        String link = entry.getLink();

        if(link == null || postRepository.existsByLink(link)) {
            return;
        }

        String title = entry.getTitle();
        String summary = entry.getDescription() != null ? entry.getDescription().getValue() : null;
        PostClassification classification = classifySafely(title, summary);
        Language language = classification != null ? classification.language() : null;
        Category category = classification != null ? classification.category() : null;

        if (!shouldKeep(language)) {
            log.debug("수집 대상 언어 아님, 스킵: {} ({})", title, language);
            return;
        }

        Post post = Post.builder()
                .title(title)
                .link(link)
                .source(source.name())
                .summary(summary)
                .language(language)
                .category(category)
                .publishedAt(toLocalDateTime(entry.getPublishedDate()))
                .build();

        postRepository.save(post);
    }

    private PostClassification classifySafely(String title, String summary) {
        try {
            return postClassifierService.classify(title, summary);
        } catch (Exception e) {
            log.warn("분류 실패, 미분류로 저장: {} ({})", title, e.getMessage());
            return null;
        }
    }

    private boolean shouldKeep(Language language) {
        return language == null || language == Language.NONE || Language.COLLECTED.contains(language);
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
