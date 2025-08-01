package io.github.lisuugi.internal.domain.types;

/**
 * Masking 유형을 지정하는 Enum 클래스입니다.
 */
public enum MaskingType {
    /**
     * 첫 글자를 제외한 나머지를 마스킹합니다. (예: 씨*)
     */
    LEAVE_FIRST_CHAR,

    /**
     * 마지막 글자를 제외한 나머지를 마스킹합니다. (예: *발)
     */
    LEAVE_LAST_CHAR,

    /**
     * 모든 글자를 마스킹합니다. (예: ****)
     */
    MASK_ALL_CHARS
}
