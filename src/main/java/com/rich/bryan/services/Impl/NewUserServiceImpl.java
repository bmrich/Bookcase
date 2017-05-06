package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.NewUserDao;
import com.rich.bryan.dao.UserDao;
import com.rich.bryan.dto.RegisterForm;
import com.rich.bryan.services.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NewUserServiceImpl implements NewUserService {

    private UserDao userDao;
    private NewUserDao newUserDao;

    @Autowired
    public NewUserServiceImpl(UserDao userDao, NewUserDao newUserDao) {
        this.userDao = userDao;
        this.newUserDao = newUserDao;
    }

    @Override
    @Transactional(dontRollbackOn = UsernameNotFoundException.class)
    public boolean newUser(RegisterForm registerForm) {
        try{
            userDao.loadUserByUsername(registerForm.getEmail());
            return false;
        } catch (UsernameNotFoundException e) {
            newUserDao.newUser(registerForm);
            return true;
        }
    }
}
