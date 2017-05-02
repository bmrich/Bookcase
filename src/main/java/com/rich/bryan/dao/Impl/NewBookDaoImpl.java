package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.NewBookDao;
import com.rich.bryan.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NewBookDaoImpl implements NewBookDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void newBook(Long id, String username) {
        Session session = sessionFactory.getCurrentSession();

        Book book = new Book();
        book.setId(id);

        User user = new User();
        user.setUsername(username);

        BooksUsers booksUsers = new BooksUsers();
        booksUsers.setBook(book);
        booksUsers.setUser(user);
        booksUsers.setState("TR");

        session.merge(booksUsers);
    }

    @Override
    public void newBook(Book book, String username){
        Session session = sessionFactory.getCurrentSession();

        User user = new User();
        user.setUsername(username);

        BooksUsers booksUsers = new BooksUsers();
        booksUsers.setUser(user);
        booksUsers.setBook(book);
        booksUsers.setState("TR");

        session.persist(booksUsers);
    }
}
