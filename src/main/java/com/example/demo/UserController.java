package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/{userId}/phones")
    public ResponseEntity<PhoneNumber> addPhoneNumber(@PathVariable Long userId, @RequestBody PhoneNumber phoneNumber) {
        return userRepository.findById(userId)
                .map(user -> {
                    phoneNumber.setUser(user);
                    return ResponseEntity.ok(phoneNumberRepository.save(phoneNumber));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/phones")
    public ResponseEntity<List<PhoneNumber>> getUserPhones(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(user.getPhoneNumbers()))
                .orElse(ResponseEntity.notFound().build());
    }
}