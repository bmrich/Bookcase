package com.rich.bryan.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "books_users")
public class BooksUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "date_created", updatable = false)
    @CreationTimestamp
    private Timestamp dateCreated;

    @Column(name = "date_started")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStarted;

    @Column(name = "date_finished")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFinished;

    @Column(name = "current_page")
    private Integer currentPage;

    @OneToMany(mappedBy = "booksUsersList", cascade = CascadeType.REMOVE)
    private Set<Shelf> shelves;

    //CR = Currently Reading, TR = To Read, R = Read
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "BooksUsers{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", dateCreated=" + dateCreated +
                ", state='" + state + '\'' +
                '}';
    }
}
