package com.example.demo.dto;

import java.util.List;

public record UserResponse(Long id, String name, List<PhoneResponse> phoneNumbers) {
}