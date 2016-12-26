package fel.si.semestral.util;

import fel.si.semestral.business.AuthorService;
import fel.si.semestral.business.BookService;
import fel.si.semestral.persistence.dao.Dao;
import fel.si.semestral.persistence.model.Author;
import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Library;
import fel.si.semestral.persistence.model.Publisher;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikolai Ogoltsov on 12/24/2016.
 */
@Singleton
@Startup
public class DBFulfillerImpl {

    @Inject
    private Dao<Author> authorDao;

    @Inject
    private Dao<Book> bookDao;

    @Inject
    private Dao<Library> libraryDao;

    @Inject
    private Dao<Publisher> publisherDao;


    @Inject
    private Logger logger;

    public DBFulfillerImpl() throws ParseException {
    }

    @PostConstruct
    public void fulFillDB() {
        publisherList.forEach(publisher -> publisherDao.create(publisher));
        libraryList.forEach(library -> libraryDao.create(library));
        bookList.forEach(book -> bookDao.create(book));
        authorList.forEach(author -> authorDao.create(author));
    }

    private DateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");

    private List<Publisher> publisherList = Arrays.asList(
            new Publisher("Albatros Media a.s.", "Na Pankráci 1618/30, Praha, Nusle"),
            new Publisher("Slovart", "Oderská 333/5, Praha, Čakovice"),
            new Publisher("Verlag Dashöfer", "Evropská 423/178, Praha, Vokovice")
    );

    private List<Library> libraryList = Arrays.asList(
            new Library("Municipal Library of Prague", "Mariánské nám. 98/1, Praha"),
            new Library("Charles University Library", "Ovocný trh 560/5, Praha"),
            new Library("Czech National Library of Technology", "Technická 2710/6, Praha")
    );

    private List<Book> bookList = Arrays.asList(
            new Book(0, formatter.parse("01-01-1890"), "The Picture of Dorian Gray"),
            new Book(1, formatter.parse("01-01-1952"), "The Old Man and the Sea"),
            new Book(2, formatter.parse("01-01-1940"), "For Whom the Bell Tolls"),
            new Book(3, formatter.parse("01-01-1602"), "Hamlet"),
            new Book(4, formatter.parse("01-01-1597"), "Romeo and Juliet"),
            new Book(5, formatter.parse("01-01-1866"), "Crime and Punishment"),
            new Book(6, formatter.parse("01-01-1880"), "The Brothers Karamazov"),
            new Book(7, formatter.parse("01-01-1915"), "The Metamorphosis")
    );

    private List<Author> authorList = Arrays.asList(
            new Author("Oscar", "Wilde", "oscar.wilde@gmail.com", bookList.subList(0, 1)),
            new Author("Ernest", "Hemingway", "ernest.hemingway@gmail.com", bookList.subList(1, 3)),
            new Author("William", "Shakespeare", "william.shakespeare@gmail.com", bookList.subList(3, 5)),
            new Author("Fyodor", "Dostoevsky", "fyodor.dostoevsky@gmail.com", bookList.subList(5, 7)),
            new Author("Franz", "Kafka", "franz.kafka@gmail.com", bookList.subList(7, 8))
    );
}
