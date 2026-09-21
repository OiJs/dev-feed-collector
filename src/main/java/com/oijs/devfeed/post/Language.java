package com.oijs.devfeed.post;

import java.util.EnumSet;
import java.util.Set;

public enum Language {
    JAVA, PYTHON, JAVASCRIPT, TYPESCRIPT, GO, KOTLIN, C_CPP, RUST, NONE;

    public static final Set<Language> COLLECTED = EnumSet.of(JAVA, JAVASCRIPT);
}