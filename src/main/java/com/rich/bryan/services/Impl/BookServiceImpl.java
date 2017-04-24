package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.BookDao;
import com.rich.bryan.entity.Book;
import com.rich.bryan.services.BookService;
import com.rich.bryan.services.Enum.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static com.rich.bryan.services.Enum.SortBy.*;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    @Transactional
    public Set<Book> getBooks(String username, SortBy sortBy) {

        List<Object[]> objects = bookDao.getBooks(username);

        Set<Book> books = new TreeSet<>(setComparator(sortBy));

        for (Object[] obj: objects){
            Book book = (Book) obj[0];
            book.setDateCreated((Timestamp) obj[1]);
            books.add(book);
        }

        return books;
    }

    private Comparator setComparator(SortBy sortBy) {
        Comparator<Book> comparator = null;
        switch (sortBy) {
            case DATE_ADDED_ASC:
                comparator = Comparator.comparing(Book::getDateCreated).thenComparing(Book::getTitle);
                break;
            case DATE_ADDED_DESC:
                comparator = Comparator.comparing(Book::getDateCreated).reversed().thenComparing(Book::getTitle);
                break;
            case PAGE_COUNT_ASC:
                comparator = Comparator.comparing(Book::getPageCount);
                break;
            case PAGE_COUNT_DESC:
                comparator = Comparator.comparing(Book::getPageCount).reversed();
                break;
            case TITLE_ASC:
                comparator = Comparator.comparing(Book::getTitle);
                break;
            case TITLE_DESC:
                comparator = Comparator.comparing(Book::getTitle).reversed();
                break;
            case AUTHOR_ASC:
                comparator = Comparator.comparing(b -> b.getAuthors().get(0).getLastName());
                break;
            case AUTHOR_DESC:
                comparator = (o1, o2) -> o2.getAuthors().get(0).getLastName().compareTo(o1.getAuthors().get(0).getLastName());
                break;
        }

        return comparator;
    }

    @Override
    @Transactional
    public void deleteBook(Long id, String name) {
        bookDao.deleteBook(id, name);
    }

    @Override
    @Transactional
    public List<Book> getAuthor(Long id, String username) {

        List<Object[]> objects = bookDao.getAuthor(id, username);

        List<Book> books = new ArrayList<>();
        for (Object[] obj: objects){
            Book book = (Book) obj[0];
            book.setDateCreated((Timestamp) obj[1]);
            books.add(book);
        }

        Collections.sort(books, setComparator(DATE_ADDED_DESC));

        return books;
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
