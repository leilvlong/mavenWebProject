package com.ssm.service;

import com.ssm.daomain.User;

public interface UserService {

    public User findIdByUser(Integer id);

    public void updateUser();
}
