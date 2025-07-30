package io.github.lisuugi.filters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SpecialCharTransitionFilterTest {

    private final SpecialCharTransitionFilter specialCharTransitionFilter = new SpecialCharTransitionFilter();
    private static final Map<String, String> testCases = new HashMap<>();

    private void addTestCases() {
        testCases.put("ㅆ|발", "ㅆㅣ발");
        testCases.put("ㅆ1발", "ㅆㅣ발");
        testCases.put("ㅂ人", "ㅂㅅ");
    }

    @Test
    void doFilter() {
        addTestCases();
        testCases.forEach((testcase, answer) ->
                Assertions.assertEquals(answer, specialCharTransitionFilter.doFilter(testcase)));
    }
}