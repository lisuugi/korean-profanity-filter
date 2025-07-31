package io.github.lisuugi.domain.types;

/**
 * 필터링 레벨을 지정하는 Enum 클래스입니다. (자세한 필터링 수준은 프로젝트 README.md 참고)
 */
public enum WordLevel {
    /**
     * 사용자 정의 단어만 필터링 합니다.
     */
    CUSTOM(0),

    /**
     * 레벨 1 수준 이하 단어를 필터링 합니다.
     */
    EXACT(1),

    /**
     * 레벨 2 수준 이하 단어를 필터링 합니다. (권장)
     */
    COMMON(2),

    /**
     * 레벨 3 수준 이하 단어를 필터링 합니다. (권장)
     */
    AGGRESSIVE(3);

    private final int value;

    WordLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
