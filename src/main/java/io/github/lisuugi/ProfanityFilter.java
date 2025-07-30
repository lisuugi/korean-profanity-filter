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

public class ProfanityFilter {

    private static final Logger logger = LoggerFactory.getLogger(ProfanityFilter.class);
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

    private void setMainMatcher(List<String> words) {
        if (words.size() <= SIMPLE_WORD_MATCHER_SIZE_LIMIT) {
            strategies.add(new SimpleWordMatcher(words));
        }
        else {
            strategies.add(new AhoCorasikMatcher(words));
        }
    }

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

    public static ProfanityFilter createDefault() {
        return builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

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

    private Collection<FoundWord> findWords(String text) {
        Collection<FoundWord> foundWords = new ArrayList<>();
        for (MatchingStrategy strategy : strategies) {
            foundWords.addAll(strategy.findWords(text));
        }
        return foundWords;
    }
}
