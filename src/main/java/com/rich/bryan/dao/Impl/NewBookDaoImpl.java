package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.NewBookDao;
import com.rich.bryan.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewBookDaoImpl implements NewBookDao {

    private SessionFactory sessionFactory;

    @Autowired
    public NewBookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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
    public List<Author> checkAuthor(Author a){
        Session session = sessionFactory.getCurrentSession();

        Query author = session.createQuery("from Author a where a.lastName=:lastName and a.firstName=:firstName")
                .setParameter("lastName", a.getLastName())
                .setParameter("firstName", a.getFirstName());

        return author.list();
    }

    @Override
    public List<Publisher> checkPublisher(Publisher p) {
        Session session = sessionFactory.getCurrentSession();

        Query publisher = session.createQuery("from Publisher p where p.publisher=:publisher")
                .setParameter("publisher", p.getPublisher());

        return publisher.list();
    }

    @Override
    public List<BooksUsers> checkBooksUsers(Long id, String username){
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from BooksUsers bu where bu.book.id=:id and bu.user.username=:username")
                .setParameter("id", id)
                .setParameter("username", username);

        return query.list();
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

        System.out.println(booksUsers);
        session.saveOrUpdate(booksUsers);
    }
}
