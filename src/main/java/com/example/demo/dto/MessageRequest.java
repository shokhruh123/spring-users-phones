package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record MessageRequest(
        @NotBlank(message = "Текст не может быть пустым") String text
) {
}