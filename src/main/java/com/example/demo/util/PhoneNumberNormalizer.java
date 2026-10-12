package com.example.demo.util;

public final class PhoneNumberNormalizer {

    private PhoneNumberNormalizer() {
    }

    public static String normalize(String raw) {
        String digits = raw.replaceAll("\\D", "");
        if (digits.length() == 9) {
            return "998" + digits;
        }
        return digits;
    }
}