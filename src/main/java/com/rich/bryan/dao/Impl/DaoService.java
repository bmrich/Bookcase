package com.rich.bryan.dao.Impl;

import com.rich.bryan.entity.Book;
import com.rich.bryan.entity.BooksUsers;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Repository
public class DaoService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Book> getBooks(String username){

        Query query = em.createQuery("select b, bu.dateCreated from BooksUsers bu " +
                "join bu.book b join fetch b.publisher join fetch b.authors where bu.user.username=:username")
                .setParameter("username", username);

        List<Object[]> objects = query.getResultList();
        List<Book> books = new ArrayList<>();
        for (Object[] obj: objects){
            Book book = (Book) obj[0];
            book.setDateCreated((Timestamp) obj[1]);
            books.add(book);
        }

        return books;
    }

    @Transactional
    public void deleteBook(Long id, String name){

        Query query = em.createQuery("from BooksUsers bu where bu.book.id=:id and bu.user.username=:name")
                .setParameter("id", id).setParameter("name", name);
        BooksUsers booksUsers = (BooksUsers) query.getSingleResult();

        em.remove(booksUsers);
    }

    @Transactional
    public List<Book> getAuthor(Long id, String username) {

        Query query = em.createQuery("select b from BooksUsers bu join bu.book b " +
                "join fetch b.authors a join fetch b.publisher join bu.user u where a.id =:id and u.username =:username")
                .setParameter("id", id).setParameter("username", username);

        List<Book> books = query.getResultList();

        return books;
    }

    @Transactional(dontRollbackOn = NoResultException.class)
    public Book getSingleBook(String isbn13) throws NoResultException{

        Query query = em.createQuery("from Book b " +
                "join fetch b.authors join fetch b.publisher where b.isbn13 =:isbn13")
                .setParameter("isbn13", isbn13);

        Book book = (Book) query.getSingleResult();
        return book;
    }
}