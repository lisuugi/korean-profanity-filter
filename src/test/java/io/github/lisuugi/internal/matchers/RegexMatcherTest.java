package io.github.lisuugi.internal.matchers;

import io.github.lisuugi.internal.matchers.RegexMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexMatcherTest {

    private final RegexMatcher regexMatcher = new RegexMatcher();

    @Test
    @DisplayName("정규식 필터 알고리즘 필터링 테스트")
    void contains() {
        boolean result1 = regexMatcher.contains("");
        assertFalse(result1);

        boolean result = regexMatcher.contains("씨발");
        assertTrue(result);

        boolean result2 = regexMatcher.contains("병신");
        assertTrue(result2);
    }

    @Test
    void findWords() {
    }
}