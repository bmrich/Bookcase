package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.UserDao;
import com.rich.bryan.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserDoaImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(dontRollbackOn = UsernameNotFoundException.class)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Session session = sessionFactory.getCurrentSession();

        UserDetails user = session.get(User.class, s);

        if (user == null){
            throw new UsernameNotFoundException("User not Found");
        }

        return user;
    }
}
