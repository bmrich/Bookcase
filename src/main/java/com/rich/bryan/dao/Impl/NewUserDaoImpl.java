package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.NewUserDao;
import com.rich.bryan.entity.Authorities;
import com.rich.bryan.entity.User;
import com.rich.bryan.dto.RegisterForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class NewUserDaoImpl implements NewUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void newUser(RegisterForm registerForm) {
        Session session = sessionFactory.getCurrentSession();

        User user = new User(registerForm.getEmail(), passwordEncoder.encode(registerForm.getPassword()));
        Authorities authorities = new Authorities("ROLE_USER", user);

        session.persist(authorities);
    }
}
