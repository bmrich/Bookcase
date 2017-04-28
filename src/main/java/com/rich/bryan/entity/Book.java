package com.rich.bryan.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(name = "isbn10", unique = true, nullable = false)
    private String isbn10;

    @Column(name = "isbn13", unique = true, nullable = false)
    private String isbn13;

    private Integer pageCount;
    private String datePublished;
    private String imageUrl;

    @Lob
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private Set<BooksUsers> users;

    @Transient
    private String dateCreated;

    @Transient
    private Timestamp timestamp;

    public Book() {}
    public Book(String title, String isbn10, String isbn13, Integer pageCount,
                String datePublished, String imageUrl, String description, Publisher publisher) {
        this.title = title;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.pageCount = pageCount;
        this.datePublished = datePublished;
        this.imageUrl = imageUrl;
        this.description = description;
        this.publisher = publisher;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.timestamp = dateCreated;
        try {
            this.dateCreated = new SimpleDateFormat("yyyy-MM-dd").format(dateCreated);
        } catch (NullPointerException e) {
            this.dateCreated = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() { return timestamp;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Set<BooksUsers> getUsers() {
        return users;
    }

    public void setUsers(Set<BooksUsers> users) {
        this.users = users;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!isbn10.equals(book.isbn10)) return false;
        return isbn13.equals(book.isbn13);
    }

    @Override
    public int hashCode() {
        int result = isbn10.hashCode();
        result = 31 * result + isbn13.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", pageCount=" + pageCount +
                ", datePublished='" + datePublished + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", publisher=" + publisher +
                ", authors=" + authors +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
