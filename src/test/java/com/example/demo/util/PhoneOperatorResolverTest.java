package com.example.demo.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneOperatorResolverTest {

    @ParameterizedTest
    @CsvSource({
            "998901234567, Beeline",
            "998911234567, Beeline",
            "998931234567, Ucell",
            "998941234567, Ucell",
            "998501234567, Ucell",
            "998951234567, Uzmobile",
            "998991234567, Uzmobile",
            "998971234567, Mobiuz",
            "998881234567, Mobiuz",
            "998331234567, Humans"
    })
    void resolvesOperatorByPrefix(String number, String expected) {
        assertThat(PhoneOperatorResolver.resolve(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "971234567, Mobiuz",
            "+998 97 123-45-67, Mobiuz",
            "(90) 123 45 67, Beeline"
    })
    void acceptsDifferentNumberFormats(String number, String expected) {
        assertThat(PhoneOperatorResolver.resolve(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"abc", "12345", "998201234567", "123456789012", "9989712345678"})
    void returnsUnknownForInvalidOrUnrecognizedNumber(String number) {
        assertThat(PhoneOperatorResolver.resolve(number)).isEqualTo("Неизвестно");
    }
}