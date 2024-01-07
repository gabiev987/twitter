package com.gabiev.twitter.domain.util;

import com.gabiev.twitter.domain.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}
