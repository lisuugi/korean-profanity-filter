package io.github.lisuugi.filters;

public interface Filter {
    void setNext(Filter filter);
    String doFilter(String text);
}
