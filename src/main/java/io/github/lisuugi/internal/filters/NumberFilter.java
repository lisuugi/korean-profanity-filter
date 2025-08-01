package io.github.lisuugi.internal.filters;

import java.util.regex.Pattern;

/**
 * 숫자와 지정된 특수문자를 감지하여 삭제하는 필터입니다.
 */
public class NumberFilter implements Filter {

    private Filter nextFilter;

    /**
     * 숫자와 지정된 특수문자를 감지하는 패턴입니다.
     * 성능 최적화를 위해 정규식 패턴을 미리 컴파일하여 재사용합니다.
     */
    private static final Pattern REMOVE_PATTERN = Pattern.compile("[0-9~.,_+=\\-@!]");

    @Override
    public void setNext(Filter filter) {
        this.nextFilter = filter;
    }

    @Override
    public String doFilter(String text) {
        String filteredText = filterLogic(text);

        if (nextFilter != null) {
            nextFilter.doFilter(filteredText);
        }
        return filteredText;
    }

    /**
     * REMOVE_PATTERN에 따라 감지된 문자를 제거하는 메서드입니다.
     * @param text 원본 문자열
     * @return 필터링 된 문자열
     */
    private String filterLogic(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return REMOVE_PATTERN.matcher(text).replaceAll("");
    }
}