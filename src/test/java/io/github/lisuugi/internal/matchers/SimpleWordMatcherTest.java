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

class SimpleWordMatcherTest {

    private static final FilterConfig filterConfig = new FilterConfig(EnumSet.allOf(WordType.class), WordLevel.EXACT, null, false);
    private final AhoCorasikMatcher ahoCorasikMatcher = new AhoCorasikMatcher(FileLoader.getWords(filterConfig));


    @Test
    @DisplayName("완전 탐색 알고리즘 필터링 테스트")
    void contains() {
        boolean result = ahoCorasikMatcher.contains("씨발");
        assertTrue(result);

        boolean result2 = ahoCorasikMatcher.contains("병신");
        assertTrue(result2);
    }

    @Test
    void findWords() {
    }
}