create table message_likes (
    user_id bigint not null references usr,
    message_id bigint references message,
    PRIMARY KEY (user_id, message_id)
)