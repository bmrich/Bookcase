package com.rich.bryan.services;

import com.rich.bryan.dto.RegisterForm;

public interface UserService {
    void deleteUser(String username);
    boolean newUser(RegisterForm registerForm);
}
