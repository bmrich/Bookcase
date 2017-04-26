package com.rich.bryan.services.Utils;

import com.rich.bryan.entity.Book;

import java.util.Comparator;

public class ComparatorHelper {

    public static Comparator setComparator(SortBy sortBy) {
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
}
