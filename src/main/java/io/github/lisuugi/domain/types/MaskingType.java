package io.github.lisuugi.domain.types;

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
