package io.github.lisuugi.matchers;

import io.github.lisuugi.dto.FoundWord;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AhoCorasikMatcher implements MatchingStrategy {
    private final Trie trie;

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
