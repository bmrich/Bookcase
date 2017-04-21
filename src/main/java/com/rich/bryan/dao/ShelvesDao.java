package com.rich.bryan.dao;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Bryan Rich on 4/21/2017.
 */
public interface ShelvesDao {
    void createShelf(String username, String shelfName);
    void addBooktoShelf(String username, String shelfName, Long bookId);
    List<String> getShelves(String username);
}
