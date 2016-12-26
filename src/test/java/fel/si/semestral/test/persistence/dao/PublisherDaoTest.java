package fel.si.semestral.test.persistence.dao;

import fel.si.semestral.persistence.model.Publisher;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Nikolai Ogoltsov on 12/17/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PublisherDaoTest extends DaoTest {

    @Test
    public void test1Create() {
        Publisher publisher = publishers.get(0);
        publisherDao.create(publisher);
        assertNotNull(publisher.getId());
    }

    @Test
    public void test2Update() {
        Publisher publisher = publishers.get(1);
        publisherDao.create(publisher);
        publisher.setName("test123");
        publisherDao.update(publisher);
        Assert.assertEquals(publisher.getName(), publisherDao.find(publisher.getId()).getName());
    }

    @Test
    public void test3Delete() {
        Publisher publisher = publishers.get(2);
        publisherDao.create(publisher);
        publisherDao.delete(publisher.getId());
    }

    @Test
    public void test4Find() {
        Publisher publisher = publishers.get(3);
        publisherDao.create(publisher);
        Assert.assertEquals(publisher, publisherDao.find(publisher.getId()));
    }

    @Test(expected = PersistenceException.class)
    public void test5DuplicatedId() {
        Publisher publisher = publishers.get(0);
        publisherDao.create(publisher);
    }

    @Test(expected = ConstraintViolationException.class)
    public void test6ValidationError() {
        publisherDao.create(new Publisher(null, ""));
    }
}
