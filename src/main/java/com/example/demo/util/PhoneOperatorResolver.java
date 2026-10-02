package com.example.demo.util;

public final class PhoneOperatorResolver {

    private PhoneOperatorResolver() {
    }

    public static String resolve(String number) {
        if (number == null) {
            return "Неизвестно";
        }

        String digits = number.replaceAll("\\D", "");

        if (digits.startsWith("998") && digits.length() == 12) {
            digits = digits.substring(3);
        }

        if (digits.length() != 9) {
            return "Неизвестно";
        }

        String prefix = digits.substring(0, 2);

        return switch (prefix) {
            case "90", "91" -> "Beeline";
            case "93", "94", "50" -> "Ucell";
            case "95", "99" -> "Uzmobile";
            case "97", "88" -> "Mobiuz";
            case "33" -> "Humans";
            default -> "Неизвестно";
        };
    }
}