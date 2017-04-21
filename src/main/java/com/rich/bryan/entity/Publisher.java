package com.rich.bryan.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publisher;

    @OneToMany(mappedBy = "publisher", cascade = {CascadeType.PERSIST})
    private Set<Book> books = new HashSet<Book>();

    public Publisher() {}
    public Publisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

//    public Set<Book> getBooks() {
//        return books;
//    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
