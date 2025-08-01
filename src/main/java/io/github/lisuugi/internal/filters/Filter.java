package io.github.lisuugi.internal.filters;

/**
 * 필터 전체 공용 Filter 인터페이스입니다. 이 인터페이스를 구현하여 다른 필터와 체이닝이 가능합니다.
 */
public interface Filter {

    /**
     * 다음 필터를 세팅합니다.
     * @param filter 다음으로 위치할 Filter 클래스
     */
    void setNext(Filter filter);

    /**
     * 현재 필터의 필터링 로직을 수행합니다.
     * @param text 원본 문자열
     * @return 필터링 된 문자열
     */
    String doFilter(String text);
}
