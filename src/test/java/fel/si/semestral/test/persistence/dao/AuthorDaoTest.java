package fel.si.semestral.test.persistence.dao;

import fel.si.semestral.persistence.model.Author;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Nikolai Ogoltsov on 11/21/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorDaoTest extends DaoTest {

    @Test
    public void test1Create() {
        Author author = authors.get(0);
        authorDao.create(author);
        assertNotNull(author.getId());
    }

    @Test
    public void test2Update() {
        Author author = authors.get(1);
        authorDao.create(author);
        author.setSurname("test123");
        authorDao.update(author);
        Assert.assertEquals("test123", authorDao.find(author.getId()).getSurname());
    }

    @Test
    public void test3Delete() {
        Author author = authors.get(2);
        authorDao.create(author);
        long id = author.getId();
        authorDao.delete(author.getId());
        assertNull(authorDao.find(id));
    }

    @Test
    public void test4Find() {
        Author author = authors.get(3);
        authorDao.create(author);
        Assert.assertEquals(author, authorDao.find(author.getId()));
    }

    @Test(expected = PersistenceException.class)
    public void test5DuplicatedId() {
        Author author = authors.get(0);
        authorDao.create(author);
    }

    @Test(expected = ConstraintViolationException.class)
    public void test6ValidationError() {
        authorDao.create(new Author("name321", "surname321", "123321231"));
    }
}
