package io.github.lisuugi.domain;

import io.github.lisuugi.domain.types.WordLevel;
import io.github.lisuugi.domain.types.WordType;

public class Dictionary {

    private final WordType type;
    private final WordLevel level;
    private final String filePath;

    public Dictionary(WordType type, WordLevel level, String filePath) {
        this.type = type;
        this.level = level;
        this.filePath = filePath;
    }

    public WordType getType() {
        return type;
    }

    public WordLevel getLevel() {
        return level;
    }

    public String getFilePath() {
        return filePath;
    }
}
