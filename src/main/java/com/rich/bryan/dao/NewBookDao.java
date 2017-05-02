package com.rich.bryan.dao;

import com.rich.bryan.entity.Book;

public interface NewBookDao {
    void newBook(Long id, String username);
    void newBook(Book book, String username);
}
