package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.NewBookDao;
import com.rich.bryan.dto.AuthorDto;
import com.rich.bryan.entity.*;
import com.rich.bryan.dto.SearchResult;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NewBookDaoImpl implements NewBookDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void newBook(SearchResult sr, String username) {
        Session session = sessionFactory.getCurrentSession();

        List<Author> authorEntities = new ArrayList<>();
        List<AuthorDto> authorDtoList = sr.getAuthorDtoList();
        for (AuthorDto a: authorDtoList) {
            Query query = session.createQuery("from Author a where a.firstName = :fname and a.lastName = :lname").
                    setParameter("fname", a.getfName()).
                    setParameter("lname", a.getlName());
            Author author = null;
            List<Author> authors = query.list();
            if (authors.isEmpty()) {
                author = new Author(a.getfName(), a.getlName());
            } else {
                author = authors.get(0);
            }
            authorEntities.add(author);
        }

        Query pubQuery = session.createQuery("from Publisher p where p.publisher = :pub").
                setParameter("pub", sr.getPublisher());
        List<Publisher> publishers = pubQuery.list();
        Publisher publisher = null;
        if (publishers.isEmpty()){
            publisher = new Publisher(sr.getPublisher());
        } else {
            publisher = publishers.get(0);
        }

        Book book = new Book(sr.getTitle(), sr.getIsbn10(), sr.getIsbn13(),
                sr.getPageCount(), sr.getDatePublished(), sr.getImageUrl(), sr.getDescription(), publisher);
        for (Author author: authorEntities) {
            book.getAuthors().add(author);
        }

        User user = new User();
        user.setUsername(username);

        BooksUsers booksUsers = new BooksUsers();
        booksUsers.setUser(user);
        booksUsers.setBook(book);
        booksUsers.setState("TR");

        session.persist(booksUsers);
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
}
