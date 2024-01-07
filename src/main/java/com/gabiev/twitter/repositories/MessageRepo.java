package com.gabiev.twitter.repositories;

import com.gabiev.twitter.domain.Message;
import com.gabiev.twitter.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Integer> {

    Page<Message> findAll(Pageable pageable);
    Page<Message> findByTag(String tag, Pageable pageable);

    Page<Message> findByAuthor(User user, Pageable pageable);
}
