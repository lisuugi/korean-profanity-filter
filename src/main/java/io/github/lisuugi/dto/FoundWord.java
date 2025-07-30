package io.github.lisuugi.dto;

public class FoundWord {
    private final String keyword;
    private final int start;
    private final int end;

    public FoundWord(String keyword, int start, int end) {
        this.keyword = keyword;
        this.start = start;
        this.end = end;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
