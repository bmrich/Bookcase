package com.rich.bryan.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsDao extends UserDetailsService {
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
