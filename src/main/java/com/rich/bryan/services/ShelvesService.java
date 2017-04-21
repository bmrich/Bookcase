package com.rich.bryan.services;

import java.util.List;

public interface ShelvesService {
    void createShelf(String username, String shelfName);
    void addBooktoShelf(String username, String shelfName, Long bookId);
    List<String> getShelves(String username);
}
