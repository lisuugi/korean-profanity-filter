package io.github.lisuugi.filters;

import java.util.regex.Pattern;

/**
 * 텍스트에서 2번 이상 연속적으로 반복되는 문자를 하나의 문자로 축약하는 필터입니다.
 * 예: "씨~~~~~발" -> "씨~발", "개애애애새끼" -> "개애새끼"
 */
public class RepeatCharFilter implements Filter {

    private Filter nextFilter;

    // [성능 최적화] 정규식 패턴을 미리 컴파일하여 재사용합니다.
    // (.) : 임의의 문자 하나를 캡처하여 그룹 1로 지정합니다.
    // \1+ : 그룹 1에서 캡처된 문자가 1번 이상 반복되는 부분을 찾습니다.
    private static final Pattern REPEAT_PATTERN = Pattern.compile("(.)\\1+");

    @Override
    public void setNext(Filter filter) {
        this.nextFilter = filter;
    }

    @Override
    public String doFilter(String text) {
        // 1. 현재 필터의 로직을 적용합니다.
        String filteredText = filterLogic(text);

        // 2. 다음 필터가 있다면, 처리된 텍스트를 다음 필터로 넘기고 그 결과를 반환합니다.
        if (nextFilter != null) {
            return nextFilter.doFilter(filteredText);
        }

        // 3. 이 필터가 마지막이라면, 최종 결과를 반환합니다.
        return filteredText;
    }

    /**
     * 실제 필터링 로직을 수행합니다.
     * 미리 컴파일된 정규식 패턴을 사용하여 반복되는 문자열을 찾아 첫 번째 문자($1)로 대체합니다.
     * @param text 원본 텍스트
     * @return 필터링된 텍스트
     */
    private String filterLogic(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        // "$1"은 정규식의 첫 번째 캡처 그룹(.)을 의미합니다.
        return REPEAT_PATTERN.matcher(text).replaceAll("$1");
    }
}