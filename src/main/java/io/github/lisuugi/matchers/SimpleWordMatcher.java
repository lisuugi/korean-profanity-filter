package io.github.lisuugi.matchers;

import io.github.lisuugi.dto.FoundWord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * O(N*M) 시간 복잡도를 가지는 Naive-Search 알고리즘을 활용한 단어 탐색 클래스입니다.
 * 단어량이 470개 이하일 때 속도가 빠릅니다.
 */
public class SimpleWordMatcher implements MatchingStrategy {

    private final List<String> keywords;

    public SimpleWordMatcher(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean contains(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

//    /**
//     * 텍스트에서 모든 키워드를 찾아 위치와 함께 반환합니다.
//     * M개의 키워드에 대해 각각 N 길이의 텍스트를 전체 탐색합니다. (O(N*M))
//     */
//    @Override
//    public Collection<FoundWord> findWords(String text) {
//        if (text == null || text.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        Collection<FoundWord> foundWords = new ArrayList<>();
//        for (String keyword : keywords) {
//            int fromIndex = 0;
//            while ((fromIndex = text.indexOf(keyword, fromIndex)) != -1) {
//                foundWords.add(new FoundWord(keyword, fromIndex, fromIndex + keyword.length()));
//                // 찾은 위치 바로 다음부터 다시 검색을 시작하여 중첩된 단어도 찾을 수 있도록 함
//                fromIndex++;
//            }
//        }
//        return foundWords;
//    }
}