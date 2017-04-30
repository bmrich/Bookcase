package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.BookDao;
import com.rich.bryan.dto.AuthorDto;
import com.rich.bryan.entity.Author;
import com.rich.bryan.entity.Book;
import com.rich.bryan.services.BookService;
import com.rich.bryan.services.Utils.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static com.rich.bryan.services.Utils.ComparatorHelper.setComparator;
import static com.rich.bryan.services.Utils.SortBy.*;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    @Transactional
    public Set<Book> getBooks(String username, SortBy sortBy) {

        List<Object[]> objects = bookDao.getBooks(username);

        List<Book> booksList = new ArrayList<>();

        for (Object[] obj: objects){
            Book book = (Book) obj[0];
            book.setDateCreated((Timestamp) obj[1]);
            booksList.add(book);
        }

        booksList.sort(setComparator(sortBy));

        return new LinkedHashSet<>(booksList);
    }

    @Override
    @Transactional
    public void deleteBook(Long id, String name) {
        bookDao.deleteBook(id, name);
    }

    @Override
    @Transactional
    public Object[] getAuthor(Long id, String username) {

        List<Object[]> objects = bookDao.getAuthor(id, username);

        List<Book> books = new ArrayList<>();
        Book b = (Book) objects.get(0)[0];
        Author a = b.getAuthors().get(0);
        for (Object[] obj: objects){
            Book book = (Book) obj[0];
            book.setDateCreated((Timestamp) obj[1]);
            books.add(book);
        }

        books.sort(setComparator(DATE_ADDED_DESC));

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

        Book book = bookDao.getSingleBook(isbn13);

        return book;
    }
}
