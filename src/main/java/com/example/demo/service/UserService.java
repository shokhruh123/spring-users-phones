package com.example.demo.service;

import com.example.demo.dto.PhoneRequest;
import com.example.demo.dto.PhoneResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.PhoneNumber;
import com.example.demo.entity.User;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PhoneMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.PhoneNumberRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.PhoneNumberNormalizer;
import com.example.demo.util.PhoneOperatorResolver;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final UserMapper userMapper;
    private final PhoneMapper phoneMapper;

    public UserService(UserRepository userRepository,
                       PhoneNumberRepository phoneNumberRepository,
                       UserMapper userMapper,
                       PhoneMapper phoneMapper) {
        this.userRepository = userRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.userMapper = userMapper;
        this.phoneMapper = phoneMapper;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll(Sort.by("id")).stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse findById(Long id) {
        return userMapper.toResponse(getUserOrThrow(id));
    }

    @Transactional
    public UserResponse create(UserRequest request) {
        User user = userMapper.toEntity(request);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = getUserOrThrow(id);
        user.setName(request.name().trim());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        User user = getUserOrThrow(id);
        userRepository.delete(user);
    }

    public List<PhoneResponse> findPhones(Long userId) {
        return getUserOrThrow(userId).getPhoneNumbers().stream()
                .map(phoneMapper::toResponse)
                .toList();
    }

    @Transactional
    public PhoneResponse addPhone(Long userId, PhoneRequest request) {
        User user = getUserOrThrow(userId);
        PhoneNumber phone = phoneMapper.toEntity(request);
        ensureNumberFree(phone.getNumber(), null);
        phone.setOperator(PhoneOperatorResolver.resolve(phone.getNumber()));
        phone.setUser(user);
        return phoneMapper.toResponse(phoneNumberRepository.save(phone));
    }

    @Transactional
    public PhoneResponse updatePhone(Long userId, Long phoneId, PhoneRequest request) {
        User user = getUserOrThrow(userId);
        PhoneNumber phone = getPhoneOrThrow(user, phoneId);
        String newNumber = PhoneNumberNormalizer.normalize(request.number());
        ensureNumberFree(newNumber, phoneId);
        phone.setNumber(newNumber);
        phone.setOperator(PhoneOperatorResolver.resolve(newNumber));
        return phoneMapper.toResponse(phone);
    }

    @Transactional
    public void deletePhone(Long userId, Long phoneId) {
        User user = getUserOrThrow(userId);
        PhoneNumber phone = getPhoneOrThrow(user, phoneId);
        user.getPhoneNumbers().remove(phone);
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + id + " не найден"));
    }

    private PhoneNumber getPhoneOrThrow(User user, Long phoneId) {
        return user.getPhoneNumbers().stream()
                .filter(phone -> phone.getId().equals(phoneId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Номер с id " + phoneId + " у пользователя " + user.getId() + " не найден"));
    }

    private void ensureNumberFree(String number, Long ownPhoneId) {
        phoneNumberRepository.findAll().stream()
                .filter(phone -> phone.getNumber().equals(number))
                .filter(phone -> ownPhoneId == null || !phone.getId().equals(ownPhoneId))
                .findAny()
                .ifPresent(phone -> {
                    throw new ConflictException("Номер " + number + " уже занят");
                });
    }
}