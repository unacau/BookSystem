package fel.si.semestral.test.persistence.dao;

import fel.si.semestral.persistence.model.Library;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Nikolai Ogoltsov on 12/17/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LibraryDaoTest extends DaoTest{

    @Test
    public void test1Create() {
        Library library = libraries.get(0);
        library.getBooks().forEach(book -> bookDao.create(book));
        libraryDao.create(library);
        assertNotNull(library.getId());
    }

    @Test
    public void test2Update() {
        Library library = libraries.get(1);
        library.getBooks().forEach(book -> bookDao.create(book));
        libraryDao.create(library);
        library.setName("test123");
        libraryDao.update(library);
        assertEquals(library.getName(), libraryDao.find(library.getId()).getName());
    }

    @Test
    public void test3Delete() {
        Library library = libraries.get(2);
        library.getBooks().forEach(book -> bookDao.create(book));
        libraryDao.create(library);
        libraryDao.delete(library.getId());
    }

    @Test
    public void test4Find() {
        Library library = libraries.get(3);
        library.getBooks().forEach(book -> bookDao.create(book));
        libraryDao.create(library);
        assertEquals(library, libraryDao.find(library.getId()));
    }

    @Test(expected = PersistenceException.class)
    public void test5DuplicatedId() {
        Library library = libraries.get(0);
        library.getBooks().forEach(book -> bookDao.create(book));
        libraryDao.create(library);
    }

    @Test(expected = ConstraintViolationException.class)
    public void test6ValidationError() {
        libraryDao.create(new Library(null, "", null));
    }
}
