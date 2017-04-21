package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.NewBookDao;
import com.rich.bryan.entity.*;
import com.rich.bryan.dto.DtoAuthor;
import com.rich.bryan.dto.SearchResult;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NewBookDaoImpl implements NewBookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void newBook(SearchResult sr, String username) {

        List<Author> authorEntities = new ArrayList<>();
        List<DtoAuthor> dtoAuthorList = sr.getDtoAuthorList();
        for (DtoAuthor a: dtoAuthorList) {
            Query query = em.createQuery("from Author a where a.firstName = :fname and a.lastName = :lname").
                    setParameter("fname", a.getfName()).
                    setParameter("lname", a.getlName());
            Author author = null;
            List<Author> authors = query.getResultList();
            if (authors.isEmpty()) {
                author = new Author(a.getfName(), a.getlName());
            } else {
                author = authors.get(0);
            }
            authorEntities.add(author);
        }

        Query pubQuery = em.createQuery("from Publisher p where p.publisher = :pub").
                setParameter("pub", sr.getPublisher());
        List<Publisher> publishers = pubQuery.getResultList();
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

        em.persist(booksUsers);
    }

    @Override
    public void newBook(Long id, String username) {
        Book book = new Book();
        book.setId(id);

        User user = new User();
        user.setUsername(username);

        BooksUsers booksUsers = new BooksUsers();
        booksUsers.setBook(book);
        booksUsers.setUser(user);

        em.merge(booksUsers);
    }
}
