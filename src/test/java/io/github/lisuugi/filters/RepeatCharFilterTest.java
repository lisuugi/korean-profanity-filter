package io.github.lisuugi.filters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class RepeatCharFilterTest {

    private final RepeatCharFilter repeatCharFilter = new RepeatCharFilter();
    private static final Map<String, String> testCases = new HashMap<>();

    private void addTestCases() {
        testCases.put("씨이이이이이발", "씨이발");
        testCases.put("ㅅㅅㅅㅅㅅㅅㅅㅅㅂㅂㅂㅂㅂㅂㅂㅂㅂ", "ㅅㅂ");
    }

    @Test
    @DisplayName("반복 문자 압축 필터 테스트")
    void doFilter() {
        addTestCases();
        testCases.forEach((testcase, answer) ->
                Assertions.assertEquals(answer, repeatCharFilter.doFilter(testcase)));
    }
}