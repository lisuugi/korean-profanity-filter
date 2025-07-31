package io.github.lisuugi;

import io.github.lisuugi.domain.types.MaskingType;
import io.github.lisuugi.domain.types.WordLevel;
import io.github.lisuugi.domain.types.WordType;
import io.github.lisuugi.dto.FilterConfig;
import io.github.lisuugi.dto.FoundWord;
import io.github.lisuugi.filters.*;
import io.github.lisuugi.matchers.MatchingStrategy;
import io.github.lisuugi.matchers.RegexMatcher;
import io.github.lisuugi.matchers.SimpleWordMatcher;
import io.github.lisuugi.utils.FileLoader;
import io.github.lisuugi.utils.MaskingUtil;
import io.github.lisuugi.matchers.AhoCorasikMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 욕설 필터 메인 클래스입니다. 빌더로 초기화해서 사용할 수 있습니다.
 */
public class ProfanityFilter {

    private static final Logger logger = LoggerFactory.getLogger(ProfanityFilter.class);

    /**
     * 해당 수치 이하인 경우 아호-코라식 알고리즘 대신 완전 탐색 알고리즘이 사용됩니다.
     */
    private static final int SIMPLE_WORD_MATCHER_SIZE_LIMIT = 470;

//    private final MaskingType maskingType;
    private final Filter filterChain;
    private final List<MatchingStrategy> strategies = new ArrayList<>();


    private ProfanityFilter(FilterConfig config) {
//        this.maskingType = config.getMaskingType();
        this.filterChain = initFilter();

        List<String> words = FileLoader.getWords(config);
        setMainMatcher(words);

        if (config.isUseRegex()) {
            strategies.add(new RegexMatcher());
        }
    }

    /**
     * 단어 수를 기준으로, 아호-코라식, 완전 탐색 알고리즘 중에 하나를 선택하는 알고리즘입니다.
     * @param words 필터링 단어 리스트
     */
    private void setMainMatcher(List<String> words) {
        if (words.size() <= SIMPLE_WORD_MATCHER_SIZE_LIMIT) {
            strategies.add(new SimpleWordMatcher(words));
        }
        else {
            strategies.add(new AhoCorasikMatcher(words));
        }
    }

    /**
     * 필터 체이닝 메서드입니다. 새로운 필터가 추가되면 이 곳만 수정하면 됩니다.
     * @return 가장 처음 시작하는 필터를 반환합니다.
     */
    private static Filter initFilter() {
        Filter specialCharTransitionFilter = new SpecialCharTransitionFilter();
        Filter numberFilter = new NumberFilter();
        Filter repeatCharFilter = new RepeatCharFilter();
        Filter concatFilter = new ConcatFilter();

        numberFilter.setNext(specialCharTransitionFilter);
        specialCharTransitionFilter.setNext(repeatCharFilter);
        repeatCharFilter.setNext(concatFilter);

        return numberFilter;
    }

    /**
     * 기본 세팅으로 욕설 필터를 제작하는 메서드입니다.
     * @return 기본 세팅값의 욕설 필터가 제작됩니다.
     */
    public static ProfanityFilter createDefault() {
        return builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 사용자 정의 욕설 필터를 제작하기 위한 빌더 클래스입니다.
     */
    public static class Builder {
        private Set<WordType> types = EnumSet.noneOf(WordType.class);
        private WordLevel level;
        private MaskingType maskingType;
        private Boolean useRegex;

        public Builder addTypes(WordType type) {
            this.types.add(type);
            return this;
        }

        public Builder addAllTypes() {
            this.types = EnumSet.allOf(WordType.class);
            return this;
        }

        public Builder setLevel(WordLevel level) {
            this.level = level;
            return this;
        }

//        public Builder setMaskingType(MaskingType maskingType) {
//            this.maskingType = maskingType;
//            return this;
//        }

        public Builder useRegexFilter(boolean enabled) {
            this.useRegex = enabled;
            return this;
        }

        public ProfanityFilter build() {
            if (this.types.isEmpty()) {
                this.types = EnumSet.allOf(WordType.class);
                logger.info("Set ALL WordTypes by default");
            }
            else {
                this.types.add(WordType.CUSTOM);
            }

            if (this.level == null) {
                this.level = WordLevel.COMMON;
                logger.info("Set WordLevel COMMON by default");
            }

//            if (this.maskingType == null) {
//                this.maskingType = MaskingType.MASK_ALL_CHARS;
//                logger.info("Set MaskingType MASK_ALL by default");
//            }

            if (this.useRegex == null) {
                this.useRegex = true;
                logger.info("Include RegexFilter by default");
            }

            return new ProfanityFilter(new FilterConfig(EnumSet.copyOf(this.types), this.level, this.maskingType, this.useRegex));
        }
    }

    /**
     * 여러 욕설 매칭 전략들을 매칭하여 욕설이 포함되어 있는지 검증합니다.
     * @param text 원본 텍스트
     * @return 욕설이 포함되었는지 여부를 true, false로 반환합니다.
     */
    public boolean containsWords(String text) {
        String filteredText = filterChain.doFilter(text);
        boolean foundWords = false;
        for (MatchingStrategy strategy : strategies) {
            foundWords |= strategy.contains(filteredText);
        }
        return foundWords;
    }

//    public String maskingWords(String text) {
//        String filteredText = filterChain.doFilter(text);
//        return MaskingUtil.mask(filteredText, findWords(filteredText), null, this.maskingType);
//    }
//
//    public String maskingWords(String text, String substituteValue) {
//        String filteredText = filterChain.doFilter(text);
//        return MaskingUtil.mask(filteredText, findWords(filteredText), substituteValue, this.maskingType);
//    }

//    private Collection<FoundWord> findWords(String text) {
//        Collection<FoundWord> foundWords = new ArrayList<>();
//        for (MatchingStrategy strategy : strategies) {
//            foundWords.addAll(strategy.findWords(text));
//        }
//        return foundWords;
//    }
}
