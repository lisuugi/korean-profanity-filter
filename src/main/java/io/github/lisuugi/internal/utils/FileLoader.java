package io.github.lisuugi.internal.utils;

import io.github.lisuugi.internal.domain.Dictionary;
import io.github.lisuugi.internal.domain.types.DictionaryFilePath;
import io.github.lisuugi.internal.domain.types.WordLevel;
import io.github.lisuugi.internal.domain.types.WordType;
import io.github.lisuugi.internal.dto.FilterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 파일에서 데이터를 추출하는 클래스입니다.
 */
public final class FileLoader {
    private static final Logger logger = LoggerFactory.getLogger(FileLoader.class);

    /**
     * 기본 파일 경로를 정의한 리스트 자료구조입니다.
     */
    private static final List<Dictionary> dictionaries = new ArrayList<>();
    static {
        dictionaries.add(new Dictionary(WordType.CUSTOM, WordLevel.CUSTOM, DictionaryFilePath.EXTERNAL_BLACKLIST.getPath()));
        dictionaries.add(new Dictionary(WordType.HARMFUL, WordLevel.EXACT, DictionaryFilePath.HARMFUL_LEVEL_1.getPath()));
        dictionaries.add(new Dictionary(WordType.HARMFUL, WordLevel.COMMON, DictionaryFilePath.HARMFUL_LEVEL_2.getPath()));
        dictionaries.add(new Dictionary(WordType.HARMFUL, WordLevel.AGGRESSIVE, DictionaryFilePath.HARMFUL_LEVEL_3.getPath()));
        dictionaries.add(new Dictionary(WordType.POLITICS, WordLevel.EXACT, DictionaryFilePath.POLITICS_LEVEL_1.getPath()));
        dictionaries.add(new Dictionary(WordType.POLITICS, WordLevel.COMMON, DictionaryFilePath.POLITICS_LEVEL_2.getPath()));
        dictionaries.add(new Dictionary(WordType.POLITICS, WordLevel.AGGRESSIVE, DictionaryFilePath.POLITICS_LEVEL_3.getPath()));
        dictionaries.add(new Dictionary(WordType.PROFANITY, WordLevel.EXACT, DictionaryFilePath.PROFANITY_LEVEL_1.getPath()));
        dictionaries.add(new Dictionary(WordType.PROFANITY, WordLevel.COMMON, DictionaryFilePath.PROFANITY_LEVEL_2.getPath()));
        dictionaries.add(new Dictionary(WordType.PROFANITY, WordLevel.AGGRESSIVE, DictionaryFilePath.PROFANITY_LEVEL_3.getPath()));
        dictionaries.add(new Dictionary(WordType.SEXUALITY, WordLevel.EXACT, DictionaryFilePath.SEXUALITY_LEVEL_1.getPath()));
        dictionaries.add(new Dictionary(WordType.SEXUALITY, WordLevel.COMMON, DictionaryFilePath.SEXUALITY_LEVEL_2.getPath()));
        dictionaries.add(new Dictionary(WordType.SEXUALITY, WordLevel.AGGRESSIVE, DictionaryFilePath.SEXUALITY_LEVEL_3.getPath()));
    }

    /**
     * FilterConfig 조건에 맞는 모든 단어를 로드하고, 화이트리스트 단어를 제외하여 반환합니다.
     *
     * @param config 필터링 조건 설정이 담긴 클래스
     * @return 읽어온 단어 목록
     */
    public static List<String> getWords(FilterConfig config) {
        Set<WordType> selectedTypes = config.getWordTypes();
        WordLevel selectedLevel = config.getLevel();

        // 1. 설정에 맞는 모든 욕설 단어(내부 사전 + 외부 블랙리스트)를 로드합니다.
        List<String> profanityWords = dictionaries.stream()
                .filter(dictionary -> selectedTypes.contains(dictionary.getType()))
                .filter(dictionary -> dictionary.getLevel().getValue() <= selectedLevel.getValue())
                .map(Dictionary::getFilePath)
                .map(FileLoader::readWordsFromFile)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        eraseBlankWords(profanityWords);
        eraseWhitelistWords(profanityWords);

        return profanityWords;
    }

    private static void eraseBlankWords(List<String> profanityWords) {
        profanityWords.remove("");
    }

    private static void eraseWhitelistWords(List<String> profanityWords) {
        List<String> whitelist = readFromExternalFile(DictionaryFilePath.EXTERNAL_WHITELIST.getPath());

        if (!whitelist.isEmpty()) {
            Set<String> whitelistSet = new HashSet<>(whitelist);
            profanityWords.removeAll(whitelistSet);
            logger.info("Words have been excluded by profanityWhitelist.txt");
        }
    }

    /**
     * 파일 경로가 외부 파일인지 내부 리소스인지 판단하여 적절한 로드 메서드를 호출하는 dispatcher 역할을 합니다.
     *
     * @param path 파일 또는 리소스의 경로
     * @return 읽어온 단어 목록
     */
    private static List<String> readWordsFromFile(String path) {
        if (isExternalDictionaryPath(path)) {
            return readFromExternalFile(path);
        } else {
            return readFromResource(path);
        }
    }

    /**
     * 클래스패스에 포함된 내부 리소스 파일을 읽습니다. 라이브러리 내부의 단어 사전을 읽는 데에 사용됩니다.
     * 이 방식은 라이브러리가 .jar 파일로 패키징되어도 안정적으로 동작합니다.
     *
     * @param resourcePath src/main/resources/ 기준의 리소스 경로
     * @return 읽어온 단어 목록
     */
    private static List<String> readFromResource(String resourcePath) {
        try (InputStream is = FileLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                logger.warn("Internal Dictionary File Not Found: {}", resourcePath);
                return Collections.emptyList();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.toList());
            }
        } catch (IOException e) {
            logger.error("Internal Dictionary File Open Error: {}", resourcePath, e);
            return Collections.emptyList();
        }
    }

    /**
     * 프로젝트 최상단에 있는 사용자 정의 파일을 읽습니다.
     *
     * @param path 프로젝트 루트 기준의 파일 경로
     * @return 읽어온 단어 목록
     */
    private static List<String> readFromExternalFile(String path) {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
             logger.info("External Dictionary File Not Found: {}", path);
            return Collections.emptyList();
        }

        try {
            List<String> words = Files.readAllLines(filePath);
            logger.info("External Dictionary File Successfully Loaded: {}", filePath.toAbsolutePath());
            return words;
        } catch (IOException e) {
            logger.error("External Dictionary File Open Error: {}", path, e);
            return Collections.emptyList();
        }
    }

    /**
     * 해당 경로가 외부 사용자 정의 파일 경로인지 확인합니다.
     */
    private static boolean isExternalDictionaryPath(String path) {
        return path.equals(DictionaryFilePath.EXTERNAL_BLACKLIST.getPath()) ||
                path.equals(DictionaryFilePath.EXTERNAL_WHITELIST.getPath());
    }
}
