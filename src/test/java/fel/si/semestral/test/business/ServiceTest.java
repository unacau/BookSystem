package fel.si.semestral.test.business;

import fel.si.semestral.business.AuthorService;
import fel.si.semestral.business.BookService;
import fel.si.semestral.business.PublisherService;
import fel.si.semestral.persistence.dao.Dao;
import fel.si.semestral.persistence.model.Author;
import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Library;
import fel.si.semestral.persistence.model.Publisher;
import fel.si.semestral.util.Resources;
import fel.si.semestral.util.exception.BookSystemException;
import fel.si.semestral.util.exception.business.AuthorServiceException;
import fel.si.semestral.util.exception.business.BookServiceException;
import fel.si.semestral.util.exception.business.PublisherServiceException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
@Transactional
@RunWith(Arquillian.class)
public abstract class ServiceTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage("fel.si.semestral.persistence.model")
                .addPackage("fel.si.semestral.persistence.dao")
                .addPackage("fel.si.semestral.business")
                .addClasses(
                        BookSystemException.class, AuthorServiceException.class,
                        BookServiceException.class, PublisherServiceException.class
                ).addPackage("fel.si.semestral.test.business")
                .addClass(Resources.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }

    @Inject
    protected AuthorService authorService;

    @Inject
    protected BookService bookService;

    @Inject
    protected PublisherService publisherService;

    @Inject
    protected Dao<Library> libraryDao;


    protected List<Author> authors = Arrays.asList(
            new Author("name100", "surname100", "email100@gmail.com"),
            new Author("name101", "surname101", "email101@gmail.com"),
            new Author("name102", "surname102", "email102@gmail.com"),
            new Author("name103", "surname103", "email103@gmail.com")
    );

    protected List<Publisher> publishers = Arrays.asList(
            new Publisher("name100", "address100"),
            new Publisher("name101", "address101"),
            new Publisher("name102", "address102"),
            new Publisher("name103", "address103")
    );

    protected List<Book> books = Arrays.asList(
            new Book(100, new Date(), "title100"),
            new Book(101, new Date(), "title101"),
            new Book(102, new Date(), "title102"),
            new Book(103, new Date(), "title103")
    );

    protected List<Library> libraries = Arrays.asList(
            new Library("name100", "address100"),
            new Library("name101", "address101"),
            new Library("name102", "address102"),
            new Library("name103", "address103")
    );
}
