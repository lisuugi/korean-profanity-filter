package io.github.lisuugi.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 분리된 한글 자모음을 완전한 글자로 조합하는 로직을 담당하는 유틸리티 클래스입니다.
 */
public final class JamoCombiner {

    // 초성, 중성, 종성의 유니코드 값을 기반으로 한 인덱스 맵
    private static final Map<Character, Integer> CHOSEONG_MAP = new HashMap<>();
    private static final Map<Character, Integer> JUNGSEONG_MAP = new HashMap<>();
    private static final Map<Character, Integer> JONGSEONG_MAP = new HashMap<>();
    private static final char[] JONGSEONG_CHARS;

    // 겹자음, 겹모음을 만들기 위한 조합 맵
    private static final Map<String, Character> DOUBLE_JONGSEONG_MAP = new HashMap<>();
    private static final Map<String, Character> DOUBLE_JUNGSEONG_MAP = new HashMap<>();

    static {
        // 초성 (Initial Consonants)
        char[] choseong = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
        for (int i = 0; i < choseong.length; i++) {
            CHOSEONG_MAP.put(choseong[i], i);
        }

        // 중성 (Medial Vowels)
        char[] jungseong = {'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'};
        for (int i = 0; i < jungseong.length; i++) {
            JUNGSEONG_MAP.put(jungseong[i], i);
        }

        // 종성 (Final Consonants)
        JONGSEONG_CHARS = new char[]{' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
        for (int i = 1; i < JONGSEONG_CHARS.length; i++) { // 종성 없음(0)은 제외
            JONGSEONG_MAP.put(JONGSEONG_CHARS[i], i);
        }

        // 종성 겹자음 조합 규칙
        DOUBLE_JONGSEONG_MAP.put("ㄱㅅ", 'ㄳ');
        DOUBLE_JONGSEONG_MAP.put("ㄴㅈ", 'ㄵ');
        DOUBLE_JONGSEONG_MAP.put("ㄴㅎ", 'ㄶ');
        DOUBLE_JONGSEONG_MAP.put("ㄹㄱ", 'ㄺ');
        DOUBLE_JONGSEONG_MAP.put("ㄹㅁ", 'ㄻ');
        DOUBLE_JONGSEONG_MAP.put("ㄹㅂ", 'ㄼ');
        DOUBLE_JONGSEONG_MAP.put("ㄹㅅ", 'ㄽ');
        DOUBLE_JONGSEONG_MAP.put("ㄹㅌ", 'ㄾ');
        DOUBLE_JONGSEONG_MAP.put("ㄹㅍ", 'ㄿ');
        DOUBLE_JONGSEONG_MAP.put("ㄹㅎ", 'ㅀ');
        DOUBLE_JONGSEONG_MAP.put("ㅂㅅ", 'ㅄ');

        // 중성 겹모음 조합 규칙
        DOUBLE_JUNGSEONG_MAP.put("ㅗㅏ", 'ㅘ');
        DOUBLE_JUNGSEONG_MAP.put("ㅗㅐ", 'ㅙ');
        DOUBLE_JUNGSEONG_MAP.put("ㅗㅣ", 'ㅚ');
        DOUBLE_JUNGSEONG_MAP.put("ㅜㅓ", 'ㅝ');
        DOUBLE_JUNGSEONG_MAP.put("ㅜㅔ", 'ㅞ');
        DOUBLE_JUNGSEONG_MAP.put("ㅜㅣ", 'ㅟ');
        DOUBLE_JUNGSEONG_MAP.put("ㅡㅣ", 'ㅢ');
    }

    private JamoCombiner() {}


    /**
     * 단어를 결합하는 메서드입니다.
     * @param text 원본 문자열
     * @return 단어 결합된 문자열
     */
    public static String combine(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder result = new StringBuilder();
        int len = text.length();
        int i = 0;

        while (i < len) {
            char c = text.charAt(i);

            // 1. 초성으로 시작하는지 확인
            if (CHOSEONG_MAP.containsKey(c)) {
                int cho = CHOSEONG_MAP.get(c);
                int jung = -1;
                int jong = 0;
                int consumed = 1;

                // 2. 중성 찾기
                if (i + 1 < len && JUNGSEONG_MAP.containsKey(text.charAt(i + 1))) {
                    jung = JUNGSEONG_MAP.get(text.charAt(i + 1));
                    consumed++;
                    // 겹모음 확인
                    if (i + 2 < len && DOUBLE_JUNGSEONG_MAP.containsKey("" + text.charAt(i + 1) + text.charAt(i + 2))) {
                        jung = JUNGSEONG_MAP.get(DOUBLE_JUNGSEONG_MAP.get("" + text.charAt(i + 1) + text.charAt(i + 2)));
                        consumed++;
                    }
                }

                // 3. 종성 찾기 (초성+중성이 있어야 함)
                if (jung != -1 && i + consumed < len && JONGSEONG_MAP.containsKey(text.charAt(i + consumed))) {
                    // 다음 글자가 모음이면 현재 글자는 종성이 아님
                    if (i + consumed + 1 >= len || !JUNGSEONG_MAP.containsKey(text.charAt(i + consumed + 1))) {
                        jong = JONGSEONG_MAP.get(text.charAt(i + consumed));
                        consumed++;
                        // 겹자음 확인
                        if (i + consumed < len && DOUBLE_JONGSEONG_MAP.containsKey("" + JONGSEONG_CHARS[jong] + text.charAt(i + consumed))) {
                            jong = JONGSEONG_MAP.get(DOUBLE_JONGSEONG_MAP.get("" + JONGSEONG_CHARS[jong] + text.charAt(i + consumed)));
                            consumed++;
                        }
                    }
                }

                if (jung != -1) { // 초성+중성 조합이 가능하면 완성된 글자 추가
                    result.append((char) (0xAC00 + (cho * 21 * 28) + (jung * 28) + jong));
                } else { // 초성만 있는 경우, 원본 초성 추가
                    result.append(c);
                }
                i += consumed;

            } else if (c >= '가' && c <= '힣') { // 4. 완성된 글자로 시작하는지 확인
                int unicode = c - 0xAC00;
                int jongIdx = unicode % 28;

                // 종성이 없고, 다음 글자가 종성이 될 수 있는지 확인
                if (jongIdx == 0 && i + 1 < len && JONGSEONG_MAP.containsKey(text.charAt(i + 1))) {
                    // 그 다음 글자가 모음이면 조합하지 않음
                    if (i + 2 >= len || !JUNGSEONG_MAP.containsKey(text.charAt(i + 2))) {
                        result.append((char) (c + JONGSEONG_MAP.get(text.charAt(i + 1))));
                        i += 2;
                        continue;
                    }
                }
                result.append(c);
                i++;
            }
            else { // 5. 자모음이나 완성 글자가 아닌 경우
                result.append(c);
                i++;
            }
        }
        return result.toString();
    }
}