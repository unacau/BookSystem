package fel.si.semestral.persistence.dao;

import fel.si.semestral.persistence.model.Book;

import javax.persistence.Query;

/**
 * Created by nogoltsov on 07.11.2016.
 */

public class BookDaoImpl extends GenericDaoImpl<Book> implements Dao<Book> {

    @Override
    public Book find(String isbn) {
        Query query = em.createQuery("from Book as b where b.isbn = :isbn");
        query.setParameter("isbn", Long.parseLong(isbn));
        return (Book) query.getSingleResult();
    }
}
