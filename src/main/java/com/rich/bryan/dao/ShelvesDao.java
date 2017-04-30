package com.rich.bryan.dao;

import com.rich.bryan.entity.Book;
import com.rich.bryan.entity.BooksUsers;

import java.util.List;

public interface ShelvesDao {
    void createShelf(String username, String shelfName);
    void addBooktoShelf(String username, String shelfName, Long bookId);
    List<Object[]> getShelves(String username);
    List<Object[]> getShelf(String username, String shelfName);
    List<String> getShelvesBookIsOn(String username, String isbn13);
    void removeFromShelf(String username, String shelfName, Long id);
    Object[] getReadingState(String username, String isbn13);
    void renameShelf(String username, String shelfName, String newShelfName);
    void deleteShelf(String username, String shelfName);
    List<BooksUsers> numBooksOnShelf(String username);
}
