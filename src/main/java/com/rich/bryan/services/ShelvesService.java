package com.rich.bryan.services;

import com.rich.bryan.entity.Book;

import java.util.List;
import java.util.Set;

public interface ShelvesService {
    void createShelf(String username, String shelfName);
    void addBooktoShelf(String username, String shelfName, Long bookId);
    List<String> getShelves(String username);
    List<Object[]> getShelvesBookIsOn(String username, String isbn13);
    Set<Book> getShelf(String username, String shelfName);
}
