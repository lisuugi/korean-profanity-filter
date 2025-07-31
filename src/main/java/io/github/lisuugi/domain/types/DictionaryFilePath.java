package io.github.lisuugi.domain.types;


/**
 * 단어 사전 위치를 기록하는 Enum 클래스입니다.
 */
public enum DictionaryFilePath {

    EXTERNAL_BLACKLIST("profanityBlacklist.txt"),
    EXTERNAL_WHITELIST("profanityWhitelist.txt"),

    HARMFUL_LEVEL_1("dictionary/harmful/harmful.level-1.exact.txt"),
    HARMFUL_LEVEL_2("dictionary/harmful/harmful.level-2.common.txt"),
    HARMFUL_LEVEL_3("dictionary/harmful/harmful.level-3.aggressive.txt"),

    POLITICS_LEVEL_1("dictionary/politics/politics.level-1.exact.txt"),
    POLITICS_LEVEL_2("dictionary/politics/politics.level-2.common.txt"),
    POLITICS_LEVEL_3("dictionary/politics/politics.level-3.aggressive.txt"),

    PROFANITY_LEVEL_1("dictionary/profanity/profanity.level-1.exact.txt"),
    PROFANITY_LEVEL_2("dictionary/profanity/profanity.level-2.common.txt"),
    PROFANITY_LEVEL_3("dictionary/profanity/profanity.level-3.aggressive.txt"),

    SEXUALITY_LEVEL_1("dictionary/sexuality/sexuality.level-1.exact.txt"),
    SEXUALITY_LEVEL_2("dictionary/sexuality/sexuality.level-2.common.txt"),
    SEXUALITY_LEVEL_3("dictionary/sexuality/sexuality.level-3.aggressive.txt");

    private final String filePath;

    DictionaryFilePath(String path) {
        this.filePath = path;
    }

    public String getPath() {
        return this.filePath;
    }
}
