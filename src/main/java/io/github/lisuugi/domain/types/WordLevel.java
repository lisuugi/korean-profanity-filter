package io.github.lisuugi.domain.types;

public enum WordLevel {
    CUSTOM(0),
    EXACT(1),
    COMMON(2),
    AGGRESSIVE(3);

    private final int value;

    WordLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
