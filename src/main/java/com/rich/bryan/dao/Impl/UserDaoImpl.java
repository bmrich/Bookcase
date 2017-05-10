package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.UserDao;
import com.rich.bryan.entity.Authorities;
import com.rich.bryan.entity.User;
import com.rich.bryan.dto.RegisterForm;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory, PasswordEncoder passwordEncoder) {
        this.sessionFactory = sessionFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void newUser(RegisterForm registerForm) {
        Session session = sessionFactory.getCurrentSession();

        User user = new User(registerForm.getEmail(), passwordEncoder.encode(registerForm.getPassword()));
        Authorities authorities = new Authorities("ROLE_USER", user);

        session.persist(authorities);
    }

    @Override
    public void deleteUser(String username) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("delete from User u where u.username=:username")
                .setParameter("username", username);

        query.executeUpdate();
    }
}
