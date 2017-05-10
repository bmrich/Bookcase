package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.UserDao;
import com.rich.bryan.dao.UserDetailsDao;
import com.rich.bryan.dto.RegisterForm;
import com.rich.bryan.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserDetailsDao userDetailsDao;
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDetailsDao userDetailsDao, UserDao userDao) {
        this.userDetailsDao = userDetailsDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        userDao.deleteUser(username);
    }

    @Override
    @Transactional(noRollbackFor = UsernameNotFoundException.class)
    public boolean newUser(RegisterForm registerForm) {
        try{
            userDetailsDao.loadUserByUsername(registerForm.getEmail());
            return false;
        } catch (UsernameNotFoundException e) {
            userDao.newUser(registerForm);
            return true;
        }
    }
}
