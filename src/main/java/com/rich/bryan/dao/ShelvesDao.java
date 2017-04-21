package com.rich.bryan.dao;

import javax.transaction.Transactional;
import java.util.List;

public interface ShelvesDao {
    void createShelf(String username, String shelfName);
    void addBooktoShelf(String username, String shelfName, Long bookId);
    List<String> getShelves(String username);
}
