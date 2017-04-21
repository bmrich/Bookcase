package com.rich.bryan.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<Authorities> authorities;

    @OneToMany(mappedBy = "book", cascade = CascadeType.DETACH)
    private Set<BooksUsers> books;

    @OneToMany(mappedBy = "userList", cascade = CascadeType.REMOVE)
    private Set<Shelves> shelves;

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<BooksUsers> getBooks() {
        return books;
    }

    public void setBooks(Set<BooksUsers> books) {
        this.books = books;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
