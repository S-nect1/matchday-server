package com.example.moim.global.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class TextUtils {
    /**
     * 문자열에서 공백, 특수문자 제거 + 소문자로 변환
     */
    public static String clean(String input) {
        if (input == null) return "";
        return input.replaceAll("[\\s\\p{Punct}]+", "").toLowerCase();
    }

    /**
     * 여러 문자열을 공백 제거 + 소문자 변환 후 이어붙이기
     */
    public static String concatClean(String delimiter, String... parts) {
        return Arrays.stream(parts)
                .filter(Objects::nonNull)
                .map(TextUtils::clean)
                .collect(Collectors.joining(delimiter));
    }
}
