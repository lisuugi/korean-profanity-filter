package io.github.lisuugi.matchers;

import io.github.lisuugi.dto.FoundWord;

import java.util.Collection;

public interface MatchingStrategy {
    boolean contains(String text);
    Collection<FoundWord> findWords(String text);
}
