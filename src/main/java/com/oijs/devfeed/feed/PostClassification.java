package com.oijs.devfeed.feed;

import com.oijs.devfeed.post.Category;
import com.oijs.devfeed.post.Language;

public record PostClassification (
        Language language,
        Category category
){
}
