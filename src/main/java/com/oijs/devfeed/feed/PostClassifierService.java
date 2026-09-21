package com.oijs.devfeed.feed;

import com.oijs.devfeed.post.Category;
import com.oijs.devfeed.post.Language;
import java.util.Arrays;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class PostClassifierService {

    private final ChatClient chatClient;

    public PostClassifierService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public PostClassification classify(String title, String summary) {
        String prompt = """
                다음 개발/기술 글의 제목과 요약을 보고 분류해줘.

                제목: "%s"
                요약: "%s"

                language는 반드시 다음 중 하나: "%s"
                category는 반드시 다음 중 하나: "%s"

                특정 프로그래밍 언어와 명확히 관련 없으면 language는 NONE으로 답해.
                """
                .formatted(title,
                        summary,
                        Arrays.toString(Language.values()),
                        Arrays.toString(Category.values())
                );

        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(PostClassification.class);
    }
}
