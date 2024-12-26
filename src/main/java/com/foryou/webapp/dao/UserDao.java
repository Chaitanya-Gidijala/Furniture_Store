package com.foryou.webapp.dao;

import com.foryou.webapp.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User theUser);
}
