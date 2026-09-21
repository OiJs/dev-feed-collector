package com.oijs.devfeed.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/meta")
public class MetaController {

    @GetMapping
    public MetaResponse getMeta() {
        return new MetaResponse(List.copyOf(Language.COLLECTED), List.of(Category.values()));
    }

    public record MetaResponse(List<Language> languages, List<Category> categories) {}
}