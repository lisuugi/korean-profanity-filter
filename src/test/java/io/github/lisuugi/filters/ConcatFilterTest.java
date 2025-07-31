package io.github.lisuugi.filters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ConcatFilterTest {

    private final ConcatFilter concatFilter = new ConcatFilter();
    private static final Map<String, String> testCases = new HashMap<>();

    private void addTestCases() {
        testCases.put("ㅆㅣㅂ년아", "씹년아");
        testCases.put("ㅆㅣ발 ㅈ같네", "씨발 ㅈ같네");
        testCases.put("ㅈㄴ 조ㅈ같은 새끼", "ㅈㄴ 좆같은 새끼");
        testCases.put("세ㄱ스하자", "섹스하자");
        testCases.put("ㅆㅣ발", "씨발");
        testCases.put("ㅂㅕㅇㅅㅣㄴ", "병신");
        testCases.put("앰ㅊ", "앰ㅊ");
    }

    @Test
    @DisplayName("문자 자음, 모음 결합 필터 테스트")
    void doFilter() {
        addTestCases();
        testCases.forEach((testcase, answer) ->
                Assertions.assertEquals(answer, concatFilter.doFilter(testcase)));
    }
}