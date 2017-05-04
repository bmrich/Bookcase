package com.rich.bryan.services;

import com.rich.bryan.entity.Book;
import com.rich.bryan.entity.BooksUsers;
import com.rich.bryan.services.Utils.Sort;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ShelvesService {
    void createShelf(String username, String shelfName);
    void addBooktoShelf(String username, String shelfName, Long bookId);
    List<Object[]> getShelves(String username);
    List<Object[]> getShelvesBookIsOn(String username, String isbn13);
    Set<Book> getShelf(String username, String shelfName, Sort sort);
    void removeFromShelf(String username, String shelfName, Long id);
    Object[] getReadingState(String username, String isbn13);
    void renameShelf(String username, String shelfName, String newShelfName);
    void deleteShelf(String username, String shelfName);
    Map<String, Integer> numBooksOnShelf(String username);
    Map<String,Integer> updateState(BooksUsers bu, String username);
    Set<Book> getPerm(String username, String shelf, Sort sort);
    Integer getCurrentPage(Long id);
}
