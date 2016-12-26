package fel.si.semestral.test.persistence.dao;

import fel.si.semestral.persistence.dao.Dao;
import fel.si.semestral.persistence.model.Author;
import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Library;
import fel.si.semestral.persistence.model.Publisher;
import fel.si.semestral.util.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Nikolai Ogoltsov on 12/17/2016.
 */
@Transactional
@RunWith(Arquillian.class)
public abstract class DaoTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage("fel.si.semestral.persistence.model")
                .addPackage("fel.si.semestral.persistence.dao")
                .addPackage("fel.si.semestral.test.persistence.dao")
                .addClass(Resources.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }

    @Inject
    protected Dao<Author> authorDao;

    @Inject
    protected Dao<Book> bookDao;

    @Inject
    protected Dao<Library> libraryDao;

    @Inject
    protected Dao<Publisher> publisherDao;

    protected List<Author> authors = Arrays.asList(
            new Author("name1", "surname1", "email1@gmail.com"),
            new Author("name2", "surname2", "email2@gmail.com"),
            new Author("name3", "surname3", "email3@gmail.com"),
            new Author("name4", "surname4", "email4@gmail.com")
    );

    protected List<Publisher> publishers = Arrays.asList(
            new Publisher("name1", "address1"),
            new Publisher("name2", "address2"),
            new Publisher("name3", "address3"),
            new Publisher("name4", "address4")
    );

    protected List<Book> books = Arrays.asList(
            new Book(1, new Date(), "title1", Collections.emptyList()),
            new Book(2, new Date(), "title2", Collections.emptyList()),
            new Book(3, new Date(), "title3", Collections.emptyList()),
            new Book(4, new Date(), "title4", Collections.emptyList())
    );

    protected List<Library> libraries = Arrays.asList(
            new Library("name1", "address1", books.subList(0, 1)),
            new Library("name2", "address2", books.subList(2, 2)),
            new Library("name3", "address3", books.subList(3, 3)),
            new Library("name4", "address4", new ArrayList<>())
    );
}
