package io.github.lisuugi.filters;

import java.util.regex.Pattern;

public class NumberFilter implements Filter {

    private Filter nextFilter;

    // [수정] 성능 최적화를 위해 정규식 패턴을 미리 컴파일하여 재사용합니다.
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

    private String filterLogic(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return REMOVE_PATTERN.matcher(text).replaceAll("");
    }
}