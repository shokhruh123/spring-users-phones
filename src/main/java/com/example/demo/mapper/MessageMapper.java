package com.example.demo.mapper;

import com.example.demo.dto.MessageRequest;
import com.example.demo.dto.MessageResponse;
import com.example.demo.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message toEntity(MessageRequest request) {
        Message message = new Message();
        message.setText(request.text().trim());
        return message;
    }

    public MessageResponse toResponse(Message message) {
        return new MessageResponse(message.getId(), message.getText());
    }
}