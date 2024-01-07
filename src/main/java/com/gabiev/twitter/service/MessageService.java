package com.gabiev.twitter.service;

import com.gabiev.twitter.domain.Message;
import com.gabiev.twitter.domain.User;
import com.gabiev.twitter.domain.dto.MessageDto;
import com.gabiev.twitter.repositories.MessageRepo;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    public Page<MessageDto> messageList(Pageable pageable, String filter, User currentUser) {
        if(filter != null && !filter.isEmpty()) {
            return messageRepo.findByTag(filter, pageable, currentUser);
        } else {
            return messageRepo.findAll(pageable, currentUser);
        }
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User author, User currentUser) {
        return messageRepo.findByAuthor(pageable, author, currentUser);
    }

    public void save(Message message) {
        messageRepo.save(message);
    }
}
