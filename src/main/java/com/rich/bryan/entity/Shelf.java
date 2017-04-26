package com.rich.bryan.entity;

import javax.persistence.*;

@Entity
@Table(name = "shelves", uniqueConstraints = @UniqueConstraint(columnNames = {"user", "bu_id", "shelf_name"}))
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shelf_name")
    private String shelfName;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User userList;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "bu_id", referencedColumnName = "id")
    private BooksUsers booksUsersList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public User getUserList() {
        return userList;
    }

    public void setUserList(User userList) {
        this.userList = userList;
    }

    public BooksUsers getBooksUsersList() {
        return booksUsersList;
    }

    public void setBooksUsersList(BooksUsers booksUsersList) {
        this.booksUsersList = booksUsersList;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "id=" + id +
                ", shelfName='" + shelfName + '\'' +
                ", userList=" + userList +
                ", booksUsersList=" + booksUsersList +
                '}';
    }
}
