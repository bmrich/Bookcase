package com.rich.bryan.dao;

import com.rich.bryan.entity.Book;

import javax.persistence.NoResultException;
import java.util.List;

public interface BookDao {
    List<Object[]> getBooks(String username);
    void deleteBook(Long id, String name);
    List<Book> getAuthor(Long id, String username);
    Book getSingleBook(String isbn13) throws NoResultException;
}
