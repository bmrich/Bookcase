package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.ShelvesDao;
import com.rich.bryan.services.ShelvesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ShelvesServiceImpl implements ShelvesService {

    @Autowired
    private ShelvesDao shelvesDao;

    @Override
    @Transactional
    public void createShelf(String username, String shelfName) {
        shelvesDao.createShelf(username, shelfName);
    }

    @Override
    @Transactional
    public void addBooktoShelf(String username, String shelfName, Long bookId) {
        shelvesDao.addBooktoShelf(username, shelfName, bookId);
    }

    @Override
    @Transactional
    public List<String> getShelves(String username) {
        return shelvesDao.getShelves(username);
    }
}
