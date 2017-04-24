package com.rich.bryan.services;

import com.rich.bryan.entity.Book;
import com.rich.bryan.services.Enum.SortBy;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

public interface BookService {
    Set<Book> getBooks(String username, SortBy sortyBy);
    void deleteBook(Long id, String name);
    List<Book> getAuthor(Long id, String username);
    Book getSingleBook(String isbn13, String username) throws NoResultException;
    Book getSingleBook(String isbn13) throws NoResultException;
}
