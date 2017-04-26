package com.rich.bryan.dao;

import com.rich.bryan.entity.Book;

import java.util.List;

public interface ShelvesDao {
    void createShelf(String username, String shelfName);
    void addBooktoShelf(String username, String shelfName, Long bookId);
    List<String> getShelves(String username);
    List<Object[]> getShelf(String username, String shelfName);
    List<String> getShelvesBookIsOn(String username, String isbn13);
}
