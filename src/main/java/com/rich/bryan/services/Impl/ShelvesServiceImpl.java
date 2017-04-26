package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.ShelvesDao;
import com.rich.bryan.entity.Book;
import com.rich.bryan.services.ShelvesService;
import com.rich.bryan.services.Utils.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.rich.bryan.services.Utils.ComparatorHelper.setComparator;

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

    @Override
    @Transactional
    public List<Object[]> getShelvesBookIsOn(String username, String isbn13) {

        List<String> shelves = shelvesDao.getShelves(username);
        List<String> shelf = shelvesDao.getShelvesBookIsOn(username, isbn13);

        List<Object[]> objects = new ArrayList<>();
        int i = 0;
        for (String shelfName : shelves){

            if (shelf.contains(shelfName)){
                objects.add(new Object[]{true, shelfName});
            } else {
                objects.add(new Object[]{false, shelfName});
            }
            i++;
        }
        return objects;
    }

    @Override
    @Transactional
    public Set<Book> getShelf(String username, String shelfName) {
        List<Object[]> objects = shelvesDao.getShelf(username, shelfName);

        Set<Book> books = new TreeSet<>(setComparator(SortBy.DATE_ADDED_DESC));
        for(Object[] object : objects){
            Book book = (Book) object[0];
            book.setDateCreated((Timestamp) object[1]);
            books.add(book);
        }

        return books;
    }
}
