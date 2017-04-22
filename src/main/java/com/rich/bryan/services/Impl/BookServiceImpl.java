package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.BookDao;
import com.rich.bryan.entity.Book;
import com.rich.bryan.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    @Transactional
    public Set<Book> getBooks(String username) {

        List<Object[]> objects = bookDao.getBooks(username);
        Set<Book> books = new HashSet<>();
        for (Object[] obj: objects){
            Book book = (Book) obj[0];
            book.setDateCreated((Timestamp) obj[1]);
            books.add(book);
        }
        System.out.println(books);
        return books;
    }

    @Override
    @Transactional
    public void deleteBook(Long id, String name) {
        bookDao.deleteBook(id, name);
    }

    @Override
    @Transactional
    public List<Book> getAuthor(Long id, String username) {
        return bookDao.getAuthor(id, username);
    }

    @Override
    @Transactional(noRollbackFor = NullPointerException.class)
    public Book getSingleBook(String isbn13) throws NullPointerException {
        return bookDao.getSingleBook(isbn13);
    }
}
