package com.example.demo.controller;

import com.example.demo.dto.PhoneRequest;
import com.example.demo.dto.PhoneResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{userId}/phones")
    public List<PhoneResponse> getPhones(@PathVariable Long userId) {
        return userService.findPhones(userId);
    }

    @PostMapping("/{userId}/phones")
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneResponse addPhone(@PathVariable Long userId, @Valid @RequestBody PhoneRequest request) {
        return userService.addPhone(userId, request);
    }

    @PutMapping("/{userId}/phones/{phoneId}")
    public PhoneResponse updatePhone(@PathVariable Long userId,
                                     @PathVariable Long phoneId,
                                     @Valid @RequestBody PhoneRequest request) {
        return userService.updatePhone(userId, phoneId, request);
    }

    @DeleteMapping("/{userId}/phones/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhone(@PathVariable Long userId, @PathVariable Long phoneId) {
        userService.deletePhone(userId, phoneId);
    }
}