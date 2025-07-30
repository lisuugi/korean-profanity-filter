package io.github.lisuugi.filters;

import io.github.lisuugi.utils.HomoglyphConverter;

public class SpecialCharTransitionFilter implements Filter {

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
        return HomoglyphConverter.convert(text);
    }
}
