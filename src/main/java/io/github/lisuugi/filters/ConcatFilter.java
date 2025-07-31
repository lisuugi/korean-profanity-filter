package io.github.lisuugi.filters;

import io.github.lisuugi.utils.JamoCombiner;

/**
 * 분리된 자음과 모음을 결합하는 필터입니다.
 */
public class ConcatFilter implements Filter {

    private Filter nextFilter;

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
        return JamoCombiner.combine(text);
    }
}
