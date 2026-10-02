package com.example.demo.mapper;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PhoneMapper phoneMapper;

    public UserMapper(PhoneMapper phoneMapper) {
        this.phoneMapper = phoneMapper;
    }

    public User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.name().trim());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getPhoneNumbers().stream().map(phoneMapper::toResponse).toList()
        );
    }
}