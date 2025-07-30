package io.github.lisuugi.filters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NumberFilterTest {

    private final NumberFilter numberFilter = new NumberFilter();
    private static final Map<String, String> testCases = new HashMap<>();

    private void addTestCases() {
        testCases.put("씨발 123 131", "씨발  ");
        testCases.put("씨1발", "씨발");
        testCases.put("123123", "");
    }

    @Test
    @DisplayName("숫자 제거 필터 검증")
    void doFilter() {
        addTestCases();
        testCases.forEach((testcase, answer) ->
                Assertions.assertEquals(answer, numberFilter.doFilter(testcase)));
    }
}