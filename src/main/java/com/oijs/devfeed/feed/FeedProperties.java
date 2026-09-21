package com.oijs.devfeed.feed;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "feed")
public record FeedProperties(
        List<FeedSource> sources
) {
}
