package com.rich.bryan.dao;

import com.rich.bryan.dto.RegisterForm;

public interface UserDao {
    void newUser(RegisterForm registerForm);
    void deleteUser(String username);
}
