package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.ShelvesDao;
import com.rich.bryan.entity.BooksUsers;
import com.rich.bryan.entity.Shelf;
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

        Shelf shelf = new Shelf();
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

        Shelf shelf = new Shelf();
        shelf.setShelfName(shelfName);
        shelf.setUserList(user);

        BooksUsers newBooksUsers = new BooksUsers();
        newBooksUsers.setId(id);

        shelf.setBooksUsersList(newBooksUsers);

        session.saveOrUpdate(shelf);
    }

    @Override
    public void removeFromShelf(String username, String shelfName, Long id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select s from BooksUsers bu join bu.shelves s " +
                "where bu.book.id=:id and s.shelfName=:shelfName and s.userList.username=:username")
                .setParameter("id", id)
                .setParameter("shelfName", shelfName)
                .setParameter("username", username);

        Shelf shelf = (Shelf) query.list().get(0);
        session.delete(shelf);
    }

    @Override
    public List<Object[]> getShelves(String username){
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select s.shelfName, count(*) from Shelf s where s.userList.username=:username group by s.shelfName")
                .setParameter("username", username);

        return query.list();
    }

    @Override
    public List<String> getShelvesBookIsOn(String username, String isbn13) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select s.shelfName from BooksUsers bu join bu.book b join bu.shelves s " +
                "where bu.user.username=:username and b.isbn13=:isbn13")
                .setParameter("username", username)
                .setParameter("isbn13", isbn13);

        return query.list();
    }

    @Override
    public Object[] getReadingState(String username, String isbn13) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select bu.id, bu.state from BooksUsers bu where bu.book.isbn13=:isbn13 and bu.user.username=:username")
                .setParameter("isbn13", isbn13)
                .setParameter("username", username);

        return (Object[]) query.list().get(0);
    }

    @Override
    public List<Object[]> getShelf(String username, String shelfName) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select b, bu.dateCreated from BooksUsers bu join bu.book b join bu.shelves s " +
                "join fetch b.authors join fetch b.publisher where s.userList.username=:username and s.shelfName=:shelfName")
                .setParameter("username", username)
                .setParameter("shelfName", shelfName);


        return query.list();
    }

    @Override
    public void renameShelf(String username, String shelfName, String newShelfName) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("update Shelf s set s.shelfName=:newShelfName " +
                "where s.shelfName=:shelfName and s.userList.username=:username")
                .setParameter("newShelfName", newShelfName)
                .setParameter("shelfName", shelfName)
                .setParameter("username", username);

        query.executeUpdate();
    }

    @Override
    public void deleteShelf(String username, String shelfName) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("delete from Shelf s where s.shelfName=:shelfName and s.userList.username=:username")
                .setParameter("shelfName", shelfName)
                .setParameter("username", username);

        query.executeUpdate();
    }

    @Override
    public List<BooksUsers> numBooksOnShelf(String username) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from BooksUsers bu where bu.user.username=:username")
                .setParameter("username", username);

        return query.list();
    }

    @Override
    public BooksUsers getBooksUsers(Long buid) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(BooksUsers.class, buid);
    }

    @Override
    public void updateState(BooksUsers bu) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(bu);
    }
}
