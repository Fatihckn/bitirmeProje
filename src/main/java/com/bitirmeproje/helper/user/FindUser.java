package com.bitirmeproje.helper.user;

import com.bitirmeproje.model.User;

public interface FindUser<T> {
    User findUser(T value);
}

