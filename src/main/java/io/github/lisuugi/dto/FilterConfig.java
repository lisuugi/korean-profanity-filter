package io.github.lisuugi.dto;

import io.github.lisuugi.domain.types.MaskingType;
import io.github.lisuugi.domain.types.WordLevel;
import io.github.lisuugi.domain.types.WordType;

import java.util.Set;

/**
 * 단어 사전 세팅 값을 저장하는 DTO 클래스입니다.
 */
public class FilterConfig {
    private final Set<WordType> wordTypes;
    private final WordLevel level;
    private final MaskingType maskingType;
    private final boolean useRegex;

    public FilterConfig(Set<WordType> wordTypes, WordLevel level, MaskingType maskingType, boolean useRegex) {
        this.wordTypes = wordTypes;
        this.level = level;
        this.maskingType = maskingType;
        this.useRegex = useRegex;
    }

    public Set<WordType> getWordTypes() {
        return wordTypes;
    }

    public WordLevel getLevel() {
        return level;
    }

    public MaskingType getMaskingType() {
        return maskingType;
    }

    public boolean isUseRegex() {
        return useRegex;
    }
}
