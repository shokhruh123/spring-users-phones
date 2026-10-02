package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneRequest(
        @NotBlank(message = "Номер не может быть пустым")
        @Pattern(regexp = "^\\+?\\d{9,12}$", message = "Некорректный номер телефона")
        String number
) {
}