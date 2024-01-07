package com.gabiev.twitter.repositories;

import com.gabiev.twitter.domain.Message;
import com.gabiev.twitter.domain.User;
import com.gabiev.twitter.domain.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepo extends CrudRepository<Message, Integer> {

    @Query( "select new com.gabiev.twitter.domain.dto.MessageDto( " +
            "     m, " +
            "     count(ml), " +
            "     sum(case when ml = :user then 1 else 0 end) > 0 " +
            ")" +
            "from Message m left join m.likes ml " +
            "group by m ")
    Page<MessageDto> findAll(Pageable pageable, @Param("user") User currentUser);

    @Query( "select new com.gabiev.twitter.domain.dto.MessageDto( " +
            "     m, " +
            "     count(ml), " +
            "     sum(case when ml = :user then 1 else 0 end) > 0 " +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.tag = :tag " +
            "group by m ")
    Page<MessageDto> findByTag(@Param("tag") String tag, Pageable pageable, @Param("user") User currentUser);

    @Query( "select new com.gabiev.twitter.domain.dto.MessageDto( " +
            "     m, " +
            "     count(ml), " +
            "     sum(case when ml = :user then 1 else 0 end) > 0 " +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.author = :author " +
            "group by m ")
    Page<MessageDto> findByAuthor(Pageable pageable, @Param("author") User author, @Param("user") User currentUser);
}
