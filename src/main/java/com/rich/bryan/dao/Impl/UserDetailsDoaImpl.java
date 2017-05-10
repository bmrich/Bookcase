package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.UserDetailsDao;
import com.rich.bryan.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UserDetailsDoaImpl implements UserDetailsDao {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDetailsDoaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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
