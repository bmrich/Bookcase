package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.UserDao;
import com.rich.bryan.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserDoaImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(dontRollbackOn = UsernameNotFoundException.class)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDetails user = em.find(User.class, s);

        if (user == null){
            throw new UsernameNotFoundException("User not Found");
        }

        return user;
    }
}
