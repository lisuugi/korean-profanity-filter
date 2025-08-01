package io.github.lisuugi.internal.filters;

import java.util.regex.Pattern;

/**
 * 텍스트에서 2번 이상 연속적으로 반복되는 문자를 하나의 문자로 축약하는 필터입니다.
 */
public class RepeatCharFilter implements Filter {

    private Filter nextFilter;

    /**
     * 반복되는 문자를 감지하는 패턴입니다.
     * 성능 최적화를 위해 정규식 패턴을 미리 컴파일하여 재사용합니다.
     */
    private static final Pattern REPEAT_PATTERN = Pattern.compile("(.)\\1+");

    @Override
    public void setNext(Filter filter) {
        this.nextFilter = filter;
    }

    @Override
    public String doFilter(String text) {
        String filteredText = filterLogic(text);

        if (nextFilter != null) {
            return nextFilter.doFilter(filteredText);
        }

        return filteredText;
    }

    /**
     * 미리 컴파일된 정규식 패턴을 사용하여 반복되는 문자열을 찾아 첫 번째 문자($1)로 대체합니다.
     * @param text 원본 텍스트
     * @return 필터링된 텍스트
     */
    private String filterLogic(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return REPEAT_PATTERN.matcher(text).replaceAll("$1");
    }
}