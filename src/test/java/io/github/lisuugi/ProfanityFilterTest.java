package io.github.lisuugi;

import io.github.lisuugi.internal.domain.types.MaskingType;
import io.github.lisuugi.internal.domain.types.WordLevel;
import io.github.lisuugi.internal.domain.types.WordType;
import io.github.lisuugi.internal.dto.FilterConfig;
import io.github.lisuugi.internal.utils.FileLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ProfanityFilterTest {

    private static final String TEST_STRING = "수박씨 발라먹어 병123신아.";

    private static final String PROFANITY_TEST_DATASET = "testDataset/profanitySentences_filtered.txt";
    private static final String CLEAN_TEST_DATASET = "testDataset/cleanSentences_test.txt";

    private static ProfanityFilter defaultFilter;
    private static List<String> profanitySentences;
    private static List<String> cleanSentences;

    @Test
    @DisplayName("기본 세팅 필터 욕 감지 테스트")
    public void testDefaultFilter() {
//        List<String> testSentences = getTestSentences(PROFANITY_TEST_DATASET);
        ProfanityFilter defaultFilter = ProfanityFilter.createDefault();

        assertTrue(defaultFilter.containsWords(TEST_STRING));

//        // [수정] assertAll을 사용하여 모든 테스트 케이스를 실행하고 실패를 한 번에 보고합니다.
//        assertAll("profanitySentences.txt의 모든 문장 검증 (욕설이 감지되어야 함)",
//                testSentences.stream()
//                        .map(sentence ->
//                                // 각 문장에 대해 assertTrue 검증을 람다식으로 반환합니다.
//                                () -> assertTrue(
//                                        defaultFilter.containsWords(sentence),
//                                        // [개선] 실패 메시지를 추가하면 어떤 문장에서 탐지를 놓쳤는지 바로 알 수 있습니다.
//                                        "욕설 감지 실패 (False Negative): [" + sentence + "]"
//                                )
//                        )
//        );
    }

    @Test
    @DisplayName("커스텀 필터 욕 감지 테스트")
    public void testCustomFilter() {
        ProfanityFilter customFilter = ProfanityFilter.builder()
                .useRegexFilter(true)
                .setLevel(WordLevel.COMMON)
//                .setMaskingType(MaskingType.MASK_ALL_CHARS)
                .addAllTypes()
                .build();

        assertTrue(customFilter.containsWords(TEST_STRING));
    }
//
//    @Test
//    @DisplayName("욕설 문장 탐지율(Recall) 측정")
//    public void testProfanityDetectionRate() {
//        testDatasetSetUp();
//        assertNotNull(profanitySentences, "profanitySentences.txt 파일을 찾을 수 없습니다.");
//
//        // 욕설 문장 중 실제로 욕설이라고 감지된 문장의 수를 계산합니다. (True Positives)
//        long detectedCount = profanitySentences.stream()
//                .filter(defaultFilter::containsWords)
//                .count();
//
//        int totalCount = profanitySentences.size();
//        double recall = (double) detectedCount / totalCount * 100;
//
//        System.out.println("--- Profanity Detection Rate (Recall) ---");
//        System.out.printf("Total profanity sentences: %d%n", totalCount);
//        System.out.printf("Correctly detected: %d%n", detectedCount);
//        System.out.printf("Detection Rate: %.2f%%%n%n", recall);
//
//        // 탐지율이 특정 임계치(예: 70%) 이상인지 검증할 수 있습니다.
//        assertTrue(recall > 70.0, "탐지율이 70% 미만입니다.");
//    }
//
//    @Test
//    @DisplayName("정상 문장 오탐지율(False Positive Rate) 측정")
//    public void testFalsePositiveRate() {
//        testDatasetSetUp();
//        assertNotNull(cleanSentences, "cleanSentences.txt 파일을 찾을 수 없습니다.");
//
//        // 정상 문장 중 욕설이라고 잘못 감지된 문장의 수를 계산합니다. (False Positives)
//
//        long falsePositiveCount = 0;
//        for (String sentence : cleanSentences) {
//            if (defaultFilter.containsWords(sentence)) {
//                falsePositiveCount++;
//                System.out.println("sentence = " + sentence);
//            }
//        }
//
//        int totalCount = cleanSentences.size();
//        double falsePositiveRate = (double) falsePositiveCount / totalCount * 100;
//
//        System.out.println("--- False Positive Rate ---");
//        System.out.printf("Total clean sentences: %d%n", totalCount);
//        System.out.printf("Incorrectly detected (False Positives): %d%n", falsePositiveCount);
//        System.out.printf("False Positive Rate: %.2f%%%n%n", falsePositiveRate);
//
//        // 오탐지율이 특정 임계치(예: 1%) 미만인지 검증할 수 있습니다.
//        assertTrue(falsePositiveRate < 1.0, "오탐지율이 1% 이상입니다.");
//    }
//
//    /**
//     * [수정] 정밀도(Precision)와 F1 Score를 함께 측정하는 테스트 메서드입니다.
//     */
//    @Test
//    @DisplayName("정밀도(Precision) 및 F1 Score 측정")
//    public void testPrecisionAndF1Score() {
//        testDatasetSetUp(); // 테스트 데이터 준비
//        assertNotNull(profanitySentences, "profanitySentences.txt 파일을 찾을 수 없습니다.");
//        assertNotNull(cleanSentences, "cleanSentences.txt 파일을 찾을 수 없습니다.");
//
//        // True Positives (TP): 욕설 문장을 욕설로 올바르게 탐지한 개수
//        long truePositiveCount = profanitySentences.stream()
//                .filter(defaultFilter::containsWords)
//                .count();
//
//        // False Positives (FP): 정상 문장을 욕설로 잘못 탐지한 개수
//        long falsePositiveCount = cleanSentences.stream()
//                .filter(defaultFilter::containsWords)
//                .count();
//
//        // Precision 계산: TP / (TP + FP)
//        double precision = 0.0;
//        if (truePositiveCount + falsePositiveCount > 0) {
//            precision = (double) truePositiveCount / (truePositiveCount + falsePositiveCount);
//        }
//
//        // Recall 계산: TP / (TP + FN) = TP / (Total Profanity Sentences)
//        double recall = 0.0;
//        if (!profanitySentences.isEmpty()) {
//            recall = (double) truePositiveCount / profanitySentences.size();
//        }
//
//        // F1 Score 계산: 2 * (Precision * Recall) / (Precision + Recall)
//        double f1Score = 0.0;
//        if (precision + recall > 0) {
//            f1Score = 2 * (precision * recall) / (precision + recall);
//        }
//
//        System.out.println("--- Precision & F1 Score ---");
//        System.out.printf("Total predictions as profanity: %d (TP: %d, FP: %d)%n", (truePositiveCount + falsePositiveCount), truePositiveCount, falsePositiveCount);
//        System.out.printf("Precision: %.2f%%%n", precision * 100);
//        System.out.printf("Recall: %.2f%%%n", recall * 100);
//        System.out.printf("F1 Score: %.2f%%%n%n", f1Score * 100);
//
//        // 정밀도가 특정 임계치(예: 99%) 이상인지 검증할 수 있습니다.
//        assertTrue(precision * 100 > 99.0, "정밀도가 99% 미만입니다.");
//        // F1 Score가 특정 임계치(예: 80%) 이상인지 검증할 수 있습니다.
//        assertTrue(f1Score * 100 > 80.0, "F1 Score가 80% 미만입니다.");
//    }
//
//
//    @Test
//    @DisplayName("단어 감지 알고리즘 속도 측정")
//    public void testWordMatcherSpeed() {
//        List<String> testSentences = new ArrayList<>();
//        testSentences.addAll(getTestSentences("benchmarkDataset/unlabeled_comments_1.txt"));
//        testSentences.addAll(getTestSentences("benchmarkDataset/unlabeled_comments_2.txt"));
//        testSentences.addAll(getTestSentences("benchmarkDataset/unlabeled_comments_3.txt"));
//        testSentences.addAll(getTestSentences("benchmarkDataset/unlabeled_comments_4.txt"));
//        testSentences.addAll(getTestSentences("benchmarkDataset/unlabeled_comments_5.txt"));
//
//        List<String> keywords = getKeywords();
//
//        long sentenceCount = testSentences.size();
//
//        System.out.println("Total Sentences: " + sentenceCount);
//        System.out.println("Total Keywords: " + keywords.size());
//        System.out.println("----------------------------------------");
//
//
//        // --- 2. 각 알고리즘의 '준비' 단계 (측정 시간에서 제외) ---
//        AhoCorasikMatcher ahoMatcher = new AhoCorasikMatcher(keywords);
//        RegexMatcher regexMatcher = new RegexMatcher();
//        SimpleWordMatcher simpleWordMatcher = new SimpleWordMatcher(keywords);
//
//        // --- 3. Aho-Corasick 성능 측정 ---
//        long startTime = System.nanoTime();
//        for (String sentence : testSentences) {
//            ahoMatcher.contains(sentence);
//        }
//        long endTime = System.nanoTime();
//        double ahoSpeedMs = (endTime - startTime) / 1_000_000.0;
//        double ahoThroughput = sentenceCount / (ahoSpeedMs / 1000.0); // [추가] 초당 처리량 계산
//        System.out.printf("Aho-Corasick O(N): %.4fms | Throughput: %,.2f sentences/sec%n", ahoSpeedMs, ahoThroughput);
//
//        // --- 4. Regex 성능 측정 ---
//        startTime = System.nanoTime();
//        for (String sentence : testSentences) {
//            regexMatcher.contains(sentence);
//        }
//        endTime = System.nanoTime();
//        double regexSpeedMs = (endTime - startTime) / 1_000_000.0;
//        double regexThroughput = sentenceCount / (regexSpeedMs / 1000.0); // [추가] 초당 처리량 계산
//        System.out.printf("Regex O(N): %.4fms | Throughput: %,.2f sentences/sec%n", regexSpeedMs, regexThroughput);
//
//        // --- 5. Naive-Search 성능 측정 ---
//        startTime = System.nanoTime();
//        for (String sentence : testSentences) {
//            simpleWordMatcher.contains(sentence);
//        }
//        endTime = System.nanoTime();
//        double naiveSpeedMs = (endTime - startTime) / 1_000_000.0;
//        double naiveThroughput = sentenceCount / (naiveSpeedMs / 1000.0); // [추가] 초당 처리량 계산
//        System.out.printf("Naive-Search O(N*M): %.4fms | Throughput: %,.2f sentences/sec%n", naiveSpeedMs, naiveThroughput);
//
//        // --- [추가] 6. Aho-Corasick + Regex '결합' 성능 측정 ---
//        startTime = System.nanoTime();
//        for (String sentence : testSentences) {
//            ahoMatcher.contains(sentence);
//            regexMatcher.contains(sentence); // 두 전략을 모두 실행
//        }
//        endTime = System.nanoTime();
//        double combinedSpeedMs = (endTime - startTime) / 1_000_000.0;
//        double combinedThroughput = sentenceCount / (combinedSpeedMs / 1000.0); // 초당 처리량 계산
//        System.out.printf("Aho-Corasick + Regex: %.4fms | Throughput: %,.2f sentences/sec%n", combinedSpeedMs, combinedThroughput);
//
//
//        // --- 7. 결과 비교 출력 ---
//        System.out.println("----------------------------------------");
//        if (ahoSpeedMs < regexSpeedMs) {
//            double percentageFaster = ((regexSpeedMs - ahoSpeedMs) / regexSpeedMs) * 100;
//            System.out.printf("Aho-Corasick is %.2f%% faster than Regex.%n", percentageFaster);
//        } else {
//            double percentageSlower = ((ahoSpeedMs - regexSpeedMs) / regexSpeedMs) * 100;
//            System.out.printf("Aho-Corasick is %.2f%% slower than Regex.%n", percentageSlower);
//        }
//        if (ahoSpeedMs < naiveSpeedMs) {
//            double percentageFaster = ((naiveSpeedMs - ahoSpeedMs) / naiveSpeedMs) * 100;
//            System.out.printf("Aho-Corasick is %.2f%% faster than Naive-Search.%n", percentageFaster);
//        } else {
//            double percentageSlower = ((ahoSpeedMs - naiveSpeedMs) / naiveSpeedMs) * 100;
//            System.out.printf("Aho-Corasick is %.2f%% slower than Naive-Search.%n", percentageSlower);
//        }
//    }

    private static List<String> getKeywords() {
        return FileLoader.getWords(
                new FilterConfig(EnumSet.allOf(WordType.class),
                        WordLevel.COMMON,
                        MaskingType.MASK_ALL_CHARS,
                        false)
        );
    }

    private static void testDatasetSetUp() {
        defaultFilter = ProfanityFilter.createDefault();
        profanitySentences = getTestSentences(PROFANITY_TEST_DATASET);
        cleanSentences = getTestSentences("cleanSentences.txt");
    }

    /**
     * 테스트 리소스 폴더(src/test/resources)에 있는 파일을 읽어와
     * 각 줄을 문자열 리스트로 반환합니다.
     *
     * @param resourceName 읽어올 파일의 이름
     * @return 파일의 각 줄이 담긴 List<String>
     */
    private static List<String> getTestSentences(String resourceName) {
        try (InputStream is = ProfanityFilterTest.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                return Collections.emptyList();
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                // [수정] 스트림을 사용하여 비어있거나 공백만 있는 라인을 필터링합니다.
                return reader.lines()
                        .filter(line -> line != null && !line.trim().isEmpty())
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}