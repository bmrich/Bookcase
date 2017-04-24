package com.rich.bryan.dao;

import com.rich.bryan.entity.Book;

import javax.persistence.NoResultException;
import java.util.List;

public interface BookDao {
    List<Object[]> getBooks(String username);
    void deleteBook(Long id, String name);
    List<Object[]> getAuthor(Long id, String username);
    Object[] getSingleBook(String isbn13, String username) throws NoResultException;
    Book getSingleBook(String isbn13) throws NoResultException;

}
