package com.rich.bryan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Entity
@Table(name = "books")
public class Book {
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

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

    @Column(name = "google_api_id")
    private String googleApiId;

    @Transient
    private Date dateCreated;

    @Transient
    private Date dateStarted;

    @Transient
    private Date dateFinished;

    @Transient
    private Integer currentPage;

    @Transient
    private Long progress;

    @Transient
    private Long buid;

    public Book() {}

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public String getGoogleApiId() {
        return googleApiId;
    }

    public void setGoogleApiId(String googleApiId) {
        this.googleApiId = googleApiId;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public Long getBuid() {
        return buid;
    }

    public void setBuid(Long buid) {
        this.buid = buid;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getDateCreated() {
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
                ", imageUrl='" + imageUrl + '\'' +
                ", publisher=" + publisher +
                ", authors=" + authors +
                ", googleApiId='" + googleApiId + '\'' +
                '}';
    }
}
