package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.ShelvesDao;
import com.rich.bryan.entity.BooksUsers;
import com.rich.bryan.entity.Shelves;
import com.rich.bryan.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShelvesDaoImpl implements ShelvesDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createShelf(String username, String shelfName){
        Session session = sessionFactory.getCurrentSession();
        User user = new User();
        user.setUsername(username);

        Shelves shelf = new Shelves();
        shelf.setShelfName(shelfName);
        shelf.setUserList(user);

        session.saveOrUpdate(shelf);
    }

    @Override
    public void addBooktoShelf(String username, String shelfName, Long bookId){
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from BooksUsers bu where bu.user.username=:username and bu.book.id=:id")
                .setParameter("username", username)
                .setParameter("id", bookId);

        List<BooksUsers> booksUsers = query.list();
        Long id = booksUsers.get(0).getId();

        User user = new User();
        user.setUsername(username);

        Shelves shelf = new Shelves();
        shelf.setShelfName(shelfName);
        shelf.setUserList(user);

        BooksUsers newBooksUsers = new BooksUsers();
        newBooksUsers.setId(id);

        shelf.setBooksUsersList(newBooksUsers);

        session.saveOrUpdate(shelf);
    }

    @Override
    public List<String> getShelves(String username){
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select s.shelfName from Shelves s where s.userList.username=:username group by s.shelfName")
                .setParameter("username", username);

        return query.list();
    }
}
