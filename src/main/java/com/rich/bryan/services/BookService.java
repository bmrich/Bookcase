package com.rich.bryan.services;

import com.rich.bryan.entity.Book;
import com.rich.bryan.services.Utils.Sort;

import javax.persistence.NoResultException;
import java.util.Set;

public interface BookService {
    Set<Book> getBooks(String username, Sort sort);
    void deleteBook(Long id, String name);
    Object[] getAuthor(Long id, String username, Sort sort);
    Book getSingleBook(String isbn13, String username) throws NoResultException;
    Book getSingleBook(String isbn13) throws NoResultException;
}
