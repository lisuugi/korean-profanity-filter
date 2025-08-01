package io.github.lisuugi.internal.utils;

/**
 * 마스킹 관련 로직을 담당하는 유틸리티 클래스입니다.
 */
public final class MaskingUtil {

//    /**
//     * private 생성자로 외부에서 인스턴스 생성을 방지합니다.
//     */
//    private MaskingUtil() {}
//
//    /**
//     * 텍스트와 찾은 욕설 정보를 받아 마스킹을 수행하는 메인 메서드입니다.
//     *
//     * @param text 원본 텍스트
//     * @param words 찾은 욕설 정보(FoundWord) 컬렉션
//     * @param substituteValue 마스킹에 사용할 문자 또는 문자열
//     * @return 마스킹 처리된 텍스트
//     */
//    public static String mask(String text, Collection<FoundWord> words, String substituteValue, MaskingType maskingType) {
//        if (words == null || words.isEmpty()) {
//            return text;
//        }
//
//        List<int[]> mergedIntervals = mergeIntervals(words);
//
//        String maskedText;
//        if (maskingType.equals(MaskingType.LEAVE_FIRST_CHAR)) {
//            maskedText = applyMasking(text, mergedIntervals, substituteValue);
//        }
//        else if (maskingType.equals(MaskingType.LEAVE_LAST_CHAR)) {
//            maskedText = applyMasking(text, mergedIntervals, substituteValue);
//        }
//        else {
//            maskedText = applyMasking(text, mergedIntervals, substituteValue);
//        }
//
//        return maskedText;
//    }
//
//    /**
//     * 겹치는 구간들을 하나의 큰 구간으로 병합합니다.
//     * 예: [1,3], [2,4] -> [1,4]
//     */
//    private static List<int[]> mergeIntervals(Collection<FoundWord> words) {
//        if (words.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        // FoundWord 리스트를 시작 위치(start) 기준으로 정렬합니다.
//        List<FoundWord> sortedWords = new ArrayList<>(words);
//        sortedWords.sort(Comparator.comparingInt(FoundWord::getStart));
//
//        List<int[]> merged = new ArrayList<>();
//        int currentStart = sortedWords.get(0).getStart();
//        int currentEnd = sortedWords.get(0).getEnd();
//
//        for (int i = 1; i < sortedWords.size(); i++) {
//            FoundWord next = sortedWords.get(i);
//            int nextStart = next.getStart();
//            int nextEnd = next.getEnd();
//
//            // 다음 구간이 현재 구간과 겹치는지 확인합니다.
//            if (nextStart < currentEnd) {
//                // 겹친다면, 현재 구간의 끝을 더 먼 쪽으로 확장합니다.
//                currentEnd = Math.max(currentEnd, nextEnd);
//            } else {
//                // 겹치지 않는다면, 현재까지 병합된 구간을 리스트에 추가하고
//                // 다음 구간을 새로운 현재 구간으로 설정합니다.
//                merged.add(new int[]{currentStart, currentEnd});
//                currentStart = nextStart;
//                currentEnd = nextEnd;
//            }
//        }
//        // 마지막으로 병합된 구간을 리스트에 추가합니다.
//        merged.add(new int[]{currentStart, currentEnd});
//
//        return merged;
//    }
//
//    /**
//     * 원본 텍스트와 병합된 구간 정보를 바탕으로 마스킹을 적용합니다.
//     */
//    private static String applyMasking(String text, List<int[]> intervals, String substituteValue) {
//        StringBuilder result = new StringBuilder();
//        int lastIndex = 0;
//
//        for (int[] interval : intervals) {
//            int start = interval[0];
//            int end = interval[1];
//
//            result.append(text, lastIndex, start);
//
//            if (substituteValue != null && substituteValue.length() == 1) {
//                for (int i = 0; i < (end - start); i++) {
//                    result.append(substituteValue);
//                }
//            } else {
//                result.append(substituteValue);
//            }
//            lastIndex = end;
//        }
//
//        if (lastIndex < text.length()) {
//            result.append(text.substring(lastIndex));
//        }
//
//        return result.toString();
//    }
}