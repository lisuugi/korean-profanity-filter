package io.github.lisuugi.internal.domain.types;

/**
 * 필터링 단어 유형을 지정하는 Enum 클래스입니다. (CUSTOM은 기본 포함)
 */
public enum WordType {

    /**
     * 사용자 정의 단어 리스트입니다.
     */
    CUSTOM,

    /**
     * 도박, 마약, 자살 관련 키워드 등 유해한 키워드를 포함합니다.
     */
    HARMFUL,

    /**
     * 정치적 비하, 남녀 갈등, 지역 갈등 키워드를 포함합니다.
     */
    POLITICS,

    /**
     * 욕설, 비하 키워드를 포함합니다.
     */
    PROFANITY,

    /**
     * 성적인 키워드를 포함합니다.
     */
    SEXUALITY
}
