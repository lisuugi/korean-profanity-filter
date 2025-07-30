package io.github.lisuugi.utils;

import io.github.lisuugi.domain.types.DictionaryFilePath;
import io.github.lisuugi.domain.types.WordLevel;
import io.github.lisuugi.domain.types.WordType;
import io.github.lisuugi.dto.FilterConfig;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {

    @Test
    void getWords() {
        FilterConfig filterConfig = new FilterConfig(EnumSet.allOf(WordType.class), WordLevel.AGGRESSIVE, null, false);

        DictionaryFilePath[] values = DictionaryFilePath.values();

        List<String> wordDatasets = Arrays.stream(DictionaryFilePath.values())
                .map(DictionaryFilePath::getPath)
                .map(this::readFromResource) // readFromResource 메서드를 호출해 List<String> 으로 변환
                .flatMap(List::stream)       // 각 List<String>을 하나의 스트림으로 평탄화
                .collect(Collectors.toList()); // 스트림의 모든 요소를 하나의 List로 수집

        eraseBlankWords(wordDatasets);
        assertEquals(FileLoader.getWords(filterConfig).size(), wordDatasets.size());
    }

    /**
     * [신규] 클래스패스에 포함된 내부 리소스 파일을 읽습니다.
     * 이 방식은 라이브러리가 .jar 파일로 패키징되어도 안정적으로 동작합니다.
     *
     * @param resourcePath src/main/resources/ 기준의 리소스 경로
     * @return 읽어온 단어 목록
     */
    private List<String> readFromResource(String resourcePath) {
        try (InputStream is = FileLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                return Collections.emptyList();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.toList());
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private static void eraseBlankWords(List<String> profanityWords) {
        profanityWords.remove("");
    }
}