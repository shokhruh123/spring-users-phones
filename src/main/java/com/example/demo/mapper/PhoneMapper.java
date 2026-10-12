package com.example.demo.mapper;

import com.example.demo.dto.PhoneRequest;
import com.example.demo.dto.PhoneResponse;
import com.example.demo.entity.PhoneNumber;
import com.example.demo.util.PhoneNumberNormalizer;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {

    public PhoneNumber toEntity(PhoneRequest request) {
        PhoneNumber phone = new PhoneNumber();
        phone.setNumber(PhoneNumberNormalizer.normalize(request.number()));
        return phone;
    }

    public PhoneResponse toResponse(PhoneNumber phone) {
        return new PhoneResponse(phone.getId(), phone.getNumber(), phone.getOperator());
    }
}