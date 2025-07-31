package io.github.lisuugi.matchers;

import io.github.lisuugi.domain.types.WordLevel;
import io.github.lisuugi.domain.types.WordType;
import io.github.lisuugi.dto.FilterConfig;
import io.github.lisuugi.utils.FileLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class AhoCorasikMatcherTest {

    private static final FilterConfig filterConfig = new FilterConfig(EnumSet.allOf(WordType.class), WordLevel.AGGRESSIVE, null, false);
    private final AhoCorasikMatcher ahoCorasikMatcher = new AhoCorasikMatcher(FileLoader.getWords(filterConfig));

    @Test
    @DisplayName("아호-코라식 알고리즘 필터링 테스트")
    void contains() {
        boolean result = ahoCorasikMatcher.contains("씨발");
        assertTrue(result);

        boolean result2 = ahoCorasikMatcher.contains("병신");
        assertTrue(result2);
    }
}