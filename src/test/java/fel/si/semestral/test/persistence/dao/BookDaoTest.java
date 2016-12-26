package fel.si.semestral.test.persistence.dao;

import fel.si.semestral.persistence.model.Book;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Nikolai Ogoltsov on 12/5/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDaoTest extends DaoTest {

    @Test
    public void test1Create() {
        Book book = books.get(0);
        bookDao.create(book);
        assertNotNull(book.getId());
    }

    @Test
    public void test2Update() {
        Book book = books.get(1);
        bookDao.create(book);
        book.setTitle("test123");
        bookDao.update(book);
        Assert.assertEquals(book.getTitle(), bookDao.find(book.getId()).getTitle());
    }

    @Test
    public void test3Delete() {
        Book book = books.get(2);
        bookDao.create(book);
        bookDao.delete(book.getId());
    }

    @Test
    public void test4Find() {
        Book book = books.get(3);
        bookDao.create(book);
        Assert.assertEquals(book, bookDao.find(book.getId()));
    }

    @Test(expected = PersistenceException.class)
    public void test5DuplicatedId() {
        Book book = books.get(0);
        bookDao.create(book);
    }

    @Test(expected = ConstraintViolationException.class)
    public void test6ValidationError() {
        bookDao.create(new Book(-123, new Date(), "", null, null));
    }
}
