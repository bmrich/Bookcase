package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.ShelvesDao;
import com.rich.bryan.entity.Book;
import com.rich.bryan.entity.BooksUsers;
import com.rich.bryan.services.ShelvesService;
import com.rich.bryan.services.Utils.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static com.rich.bryan.services.Utils.SortMethod.getSortMethod;

@Service
public class ShelvesServiceImpl implements ShelvesService {

    private ShelvesDao shelvesDao;

    @Autowired
    public ShelvesServiceImpl(ShelvesDao shelvesDao) {
        this.shelvesDao = shelvesDao;
    }

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
    public void removeFromShelf(String username, String shelfName, Long id) {
        shelvesDao.removeFromShelf(username, shelfName, id);
    }

    @Override
    @Transactional
    public List<Object[]> getShelves(String username) {
        return shelvesDao.getShelves(username);
    }

    @Override
    @Transactional
    public Object[] getReadingState(String username, String isbn13) {
        return shelvesDao.getReadingState(username, isbn13);
    }

    @Override
    @Transactional
    public List<Object[]> getShelvesBookIsOn(String username, String isbn13) {

        List<Object[]> shelves = shelvesDao.getShelves(username);
        List<String> shelf = shelvesDao.getShelvesBookIsOn(username, isbn13);

        List<Object[]> objects = new ArrayList<>();
        for (Object[] shelfName : shelves){
            if (shelf.contains(shelfName[0])){
                objects.add(new Object[]{true, shelfName[0]});
            } else {
                objects.add(new Object[]{false, shelfName[0]});
            }
        }
        return objects;
    }

    @Override
    @Transactional
    public Set<Book> getShelf(String username, String shelfName, Sort sort) {
        List<Object[]> objects = shelvesDao.getShelf(username, shelfName, getSortMethod(sort));

        Set<Book> books = new LinkedHashSet<>();
        for(Object[] object : objects){
            Book book = (Book) object[0];
            book.setDateCreated((Timestamp) object[1]);
            books.add(book);
        }

        return books;
    }

    @Override
    @Transactional
    public void renameShelf(String username, String shelfName, String newShelfName) {
        shelvesDao.renameShelf(username, shelfName, newShelfName);
    }

    @Override
    @Transactional
    public void deleteShelf(String username, String shelfName) {
        shelvesDao.deleteShelf(username, shelfName);
    }

    @Override
    @Transactional
    public Map<String, Integer> numBooksOnShelf(String username) {
        List<BooksUsers> users = shelvesDao.numBooksOnShelf(username);

        int tr = 0, cr = 0, r = 0;
        for(BooksUsers bu : users){
            String state = bu.getState();
            if(state.equals("TR")){
                tr++;
            } else if(state.equals("CR")){
                cr++;
            } else {
                r++;
            }
        }

        Map<String, Integer> map = new HashMap<>();
        map.put("CR", cr);
        map.put("TR", tr);
        map.put("R", r);
        map.put("All", cr+tr+r);

        return map;
    }

    @Override
    @Transactional
    public void updateState(BooksUsers bu) {
        shelvesDao.getBooksUsers(bu);
    }

    @Override
    @Transactional
    public Integer getCurrentPage(Long id) {
        return shelvesDao.getCurrentPage(id);
    }

    @Override
    @Transactional
    public Set<Book> getPerm(String username, String shelf, Sort sortMethod) {

        List<BooksUsers> list = shelvesDao.getPerm(username, shelf, getSortMethod(sortMethod));

        Set<Book> books = new LinkedHashSet<>();
        for(BooksUsers item : list){
            Book b = item.getBook();
            b.setDateCreated(item.getDateCreated());
            b.setDateFinished(item.getDateFinished());
            b.setDateStarted(item.getDateStarted());
            b.setCurrentPage(item.getCurrentPage());

            if (item.getState().equals("CR")) {
                b.setProgress(Math.round(((item.getCurrentPage().doubleValue() / b.getPageCount()) * 100)));
                b.setBuid(item.getId());
            }
            books.add(b);
        }

        return books;
    }
}
