package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.BookDao;
import com.rich.bryan.entity.Author;
import com.rich.bryan.entity.Book;
import com.rich.bryan.services.BookService;
import com.rich.bryan.services.Utils.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static com.rich.bryan.services.Utils.SortMethod.getSortMethod;

@Service
public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    @Transactional
    public Set<Book> getBooks(String username, Sort sort) {

        List<Object[]> objects = bookDao.getBooks(username, getSortMethod(sort));

        List<Book> booksList = new ArrayList<>();

        for (Object[] obj: objects){
            Book book = (Book) obj[0];
            book.setDateCreated((Timestamp) obj[1]);
            booksList.add(book);
        }

        return new LinkedHashSet<>(booksList);
    }

    @Override
    @Transactional
    public void deleteBook(Long id, String name) {
        bookDao.deleteBook(id, name);
    }

    @Override
    @Transactional
    public Object[] getAuthor(Long id, String username, Sort sort) {

        List<Object[]> objects = bookDao.getAuthor(id, username, getSortMethod(sort));

        List<Book> books = new ArrayList<>();

        Book b = (Book) objects.get(0)[0];
        Author a = b.getAuthors().get(0);
        for (Object[] obj: objects){
            Book book = (Book) obj[0];
            book.setDateCreated((Timestamp) obj[1]);
            books.add(book);
        }

        String name = a.getFirstName() +" "+ a.getLastName();

        return new Object[]{name, books};
    }

    @Override
    @Transactional(noRollbackFor = NullPointerException.class)
    public Book getSingleBook(String isbn13, String username) throws NullPointerException {

        Object[] object = bookDao.getSingleBook(isbn13, username);

        Book book = (Book) object[0];
        book.setDateCreated((Timestamp) object[1]);

        return book;
    }

    @Override
    @Transactional(noRollbackFor = NullPointerException.class)
    public Book getSingleBook(String isbn13) throws NullPointerException {
        List<Book> book = bookDao.getSingleBook(isbn13);
        return book.get(0);
    }
}
