package com.example.demo.service;

import com.example.demo.dto.MessageRequest;
import com.example.demo.dto.MessageResponse;
import com.example.demo.entity.Message;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.repository.MessageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public List<MessageResponse> findAll() {
        return messageRepository.findAll(Sort.by("id")).stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    public MessageResponse findById(Long id) {
        return messageMapper.toResponse(getOrThrow(id));
    }

    @Transactional
    public MessageResponse create(MessageRequest request) {
        Message message = messageMapper.toEntity(request);
        return messageMapper.toResponse(messageRepository.save(message));
    }

    @Transactional
    public MessageResponse update(Long id, MessageRequest request) {
        Message message = getOrThrow(id);
        message.setText(request.text().trim());
        return messageMapper.toResponse(messageRepository.save(message));
    }

    @Transactional
    public void delete(Long id) {
        messageRepository.delete(getOrThrow(id));
    }

    private Message getOrThrow(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сообщение с id " + id + " не найдено"));
    }
}