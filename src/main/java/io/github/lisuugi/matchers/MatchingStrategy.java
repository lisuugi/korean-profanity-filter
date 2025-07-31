package io.github.lisuugi.matchers;

import io.github.lisuugi.dto.FoundWord;

import java.util.Collection;

/**
 * 단어 감지 전략 인터페이스입니다. 다양한 감지 전략을 이 인터페이스를 구현하는 것으로 추가할 수 있습니다.
 */
public interface MatchingStrategy {

    /**
     * 찾고자 하는 단어들이 원본 문자열에 포함되어 있는지를 감지하여 boolean으로 반환합니다.
     * @param text 원본 문자열
     * @return 단어 탐지 여부
     */
    boolean contains(String text);
//    Collection<FoundWord> findWords(String text);
}
