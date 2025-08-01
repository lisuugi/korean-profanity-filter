package io.github.lisuugi.internal.filters;

import io.github.lisuugi.internal.filters.SpecialCharTransitionFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class SpecialCharTransitionFilterTest {

    private final SpecialCharTransitionFilter specialCharTransitionFilter = new SpecialCharTransitionFilter();
    private static final Map<String, String> testCases = new HashMap<>();

    private void addTestCases() {
        testCases.put("ㅆ|발", "ㅆㅣ발");
        testCases.put("ㅆ1발", "ㅆㅣ발");
        testCases.put("ㅂ人", "ㅂㅅ");
    }

    @Test
    @DisplayName("특수문자 자모음 치환 테스트")
    void doFilter() {
        addTestCases();
        testCases.forEach((testcase, answer) ->
                Assertions.assertEquals(answer, specialCharTransitionFilter.doFilter(testcase)));
    }
}