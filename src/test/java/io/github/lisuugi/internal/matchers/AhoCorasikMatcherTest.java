package io.github.lisuugi.internal.matchers;

import io.github.lisuugi.internal.domain.types.WordLevel;
import io.github.lisuugi.internal.domain.types.WordType;
import io.github.lisuugi.internal.dto.FilterConfig;
import io.github.lisuugi.internal.matchers.AhoCorasikMatcher;
import io.github.lisuugi.internal.utils.FileLoader;
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
        boolean result1 = ahoCorasikMatcher.contains("");
        assertFalse(result1);

        boolean result2 = ahoCorasikMatcher.contains("씨발");
        assertTrue(result2);

        boolean result3 = ahoCorasikMatcher.contains("병신");
        assertTrue(result3);
    }
}