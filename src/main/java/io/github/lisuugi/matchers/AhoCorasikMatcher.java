package io.github.lisuugi.matchers;

import io.github.lisuugi.dto.FoundWord;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 아호-코라식 알고리즘을 활용한 단어 탐색 클래스입니다.
 */
public class AhoCorasikMatcher implements MatchingStrategy {
    private final Trie trie;

    /**
     * 감지하는 단어들을 넣으면, 해당 단어들을 기반으로 Trie 트리를 생성합니다.
     * @param keywords 감지 단어 목록
     */
    public AhoCorasikMatcher(List<String> keywords) {
        this.trie = Trie.builder().addKeywords(keywords).build();
    }

    @Override
    public boolean contains(String text) {
        return trie.containsMatch(text);
    }

//    @Override
//    public Collection<FoundWord> findWords(String text) {
//        Collection<Emit> emits = trie.parseText(text);
//        return emits.stream()
//                .map(emit -> new FoundWord(emit.getKeyword(), emit.getStart(), emit.getEnd() + 1))
//                .collect(Collectors.toList());
//    }
}
