package com.dungnt.service;

import com.dungnt.entity.Order;
import com.dungnt.entity.User;
import com.dungnt.util.CommonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {
    @Inject
    CommonUtils utils;

    @Transactional
    public User createUser() {
        User user = User.builder().userId(utils.generateULID()).build();
        user.persist();
        return user;
    }
}
