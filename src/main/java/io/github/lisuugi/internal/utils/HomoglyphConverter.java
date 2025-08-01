package io.github.lisuugi.internal.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 유사 문자(Homoglyph) 및 특수문자를 한글 자모음으로 변환하는 로직을 담당하는 유틸리티 클래스입니다.
 */
public final class HomoglyphConverter {

    private static final Map<Character, Character> HOMOGLYPH_MAP = new HashMap<>();
    static {
        // --- 자음 (Consonants) ---
        // ㄱ
        HOMOGLYPH_MAP.put('7', 'ㄱ'); // 숫자 7
        HOMOGLYPH_MAP.put('L', 'ㄱ'); // 알파벳 L (ㄴ으로도 사용될 수 있어 모호함)

        // ㄷ
        HOMOGLYPH_MAP.put('E', 'ㄷ'); // 알파벳 E (ㅌ으로도 사용될 수 있어 모호함)
        HOMOGLYPH_MAP.put('C', 'ㄷ'); // 알파벳 C
        HOMOGLYPH_MAP.put('С', 'ㄷ'); // 키릴 문자 Es

        // ㄹ
        HOMOGLYPH_MAP.put('S', 'ㄹ'); // 알파벳 S
        HOMOGLYPH_MAP.put('Z', 'ㄹ'); // 알파벳 Z (ㅈ으로도 사용될 수 있어 모호함)

        // ㅁ
        HOMOGLYPH_MAP.put('ㅁ', 'ㅁ'); // 한자 입 구
        HOMOGLYPH_MAP.put('ם', 'ㅁ'); // 히브리 문자 Mem Sofit

        // ㅂ
        HOMOGLYPH_MAP.put('H', 'ㅂ'); // 알파벳 H
        HOMOGLYPH_MAP.put('B', 'ㅂ'); // 알파벳 B
        HOMOGLYPH_MAP.put('Н', 'ㅂ'); // 키릴 문자 En

        // ㅅ
        HOMOGLYPH_MAP.put('人', 'ㅅ'); // 한자 사람 인
        HOMOGLYPH_MAP.put('^', 'ㅅ'); // 캐럿

        // ㅇ
        HOMOGLYPH_MAP.put('O', 'ㅇ'); // 알파벳 O (대문자)
        HOMOGLYPH_MAP.put('o', 'ㅇ'); // 알파벳 o (소문자)
        HOMOGLYPH_MAP.put('0', 'ㅇ'); // 숫자 0
        HOMOGLYPH_MAP.put('○', 'ㅇ'); // 원 문자
        HOMOGLYPH_MAP.put('◯', 'ㅇ'); // 큰 원
        HOMOGLYPH_MAP.put('Ο', 'ㅇ'); // 그리스 문자 Omicron

        // ㅋ
        HOMOGLYPH_MAP.put('F', 'ㅋ'); // 알파벳 F

        // ㄲ
        HOMOGLYPH_MAP.put('刀', 'ㄲ'); // 한자 칼 도

        // --- 모음 (Vowels) ---
        // ㅣ
        HOMOGLYPH_MAP.put('|', 'ㅣ'); // 버티컬 바
        HOMOGLYPH_MAP.put('l', 'ㅣ'); // 알파벳 l (소문자)
        HOMOGLYPH_MAP.put('I', 'ㅣ'); // 알파벳 I (대문자)
        HOMOGLYPH_MAP.put('1', 'ㅣ'); // 숫자 1

        // ㅡ
        HOMOGLYPH_MAP.put('ㅡ', 'ㅡ'); // 한자 한 일
        HOMOGLYPH_MAP.put('-', 'ㅡ'); // 하이픈
    }

    private HomoglyphConverter() {}

    /**
     * 입력된 텍스트의 각 문자를 순회하며 유사 문자를 해당하는 자모음으로 변환합니다.
     * @param text 변환할 원본 텍스트
     * @return 변환된 텍스트
     */
    public static String convert(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder sb = new StringBuilder(text.length());
        for (char c : text.toCharArray()) {
            // 맵에 변환할 문자가 있으면 변환된 문자를, 없으면 원래 문자를 추가합니다.
            sb.append(HOMOGLYPH_MAP.getOrDefault(c, c));
        }
        return sb.toString();
    }
}
