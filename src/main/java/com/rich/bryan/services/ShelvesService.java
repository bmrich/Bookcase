package com.rich.bryan.services;

import com.rich.bryan.entity.Book;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ShelvesService {
    void createShelf(String username, String shelfName);
    void addBooktoShelf(String username, String shelfName, Long bookId);
    List<Object[]> getShelves(String username);
    List<Object[]> getShelvesBookIsOn(String username, String isbn13);
    Set<Book> getShelf(String username, String shelfName);
    void removeFromShelf(String username, String shelfName, Long id);
    Object[] getReadingState(String username, String isbn13);
    void renameShelf(String username, String shelfName, String newShelfName);
    void deleteShelf(String username, String shelfName);
    Map<String, Integer> numBooksOnShelf(String username);
}
