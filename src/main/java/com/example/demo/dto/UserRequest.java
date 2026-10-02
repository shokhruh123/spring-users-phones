package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "Имя не может быть пустым") String name
) {
}