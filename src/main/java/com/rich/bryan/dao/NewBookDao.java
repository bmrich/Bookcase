package com.rich.bryan.dao;

import com.rich.bryan.entity.Author;
import com.rich.bryan.entity.Book;
import com.rich.bryan.entity.BooksUsers;
import com.rich.bryan.entity.Publisher;

import java.util.List;

public interface NewBookDao {
    void newBook(Long id, String username);
    void newBook(Book book, String username);
    List<Author> checkAuthor(Author a);
    List<Publisher> checkPublisher(Publisher p);
    List<BooksUsers> checkBooksUsers(Long id, String username);
}
