package fel.si.semestral.test.business;

import fel.si.semestral.persistence.model.Author;
import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Library;
import fel.si.semestral.persistence.model.Publisher;
import fel.si.semestral.util.exception.business.BookServiceException;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddBookToLibraryTest extends ServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test1BookDoesNotExist() {
        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Book with isbn [12345] does not exist.");
        bookService.addBookToLibrary(12345, "1234232");
    }

    @Test
    public void test2PublisherDoesNotExist() {
        Book book = books.get(0);
        bookService.create(book);

        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Library with name [blahblahblah] does not exist.");
        bookService.addBookToLibrary(book.getIsbn(), "blahblahblah");
    }

    @Test
    public void test3BookWasNotPublished() {
        Book book = books.get(1);
        Library library = libraries.get(0);
        for (int i = 0; i < 5; i++) {
            library.addBook(book);
        }

        bookService.create(book);
        libraryDao.create(library);

        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Book [title101] was not published.");
        bookService.addBookToLibrary(book.getIsbn(), library.getName());
    }

    @Test
    public void test4Add() {
        Book book = books.get(2);
        Author author = authors.get(0);
        Library library = libraries.get(1);
        Publisher publisher = publishers.get(0);

        bookService.create(book);
        author.addBook(book);
        book.addAuthor(author);
        authorService.create(author);
        publisherService.create(publisher);

        authorService.contractWithPublisher(book.getAuthors().get(0).getEmail(), publisher.getName());
        publisherService.publishBook(publisher.getName(), book.getIsbn());

        libraryDao.create(library);
        bookService.addBookToLibrary(book.getIsbn(), library.getName());

        assertTrue(libraryDao.find(library.getName()).getBooks().size() == 1);
        assertTrue(bookService.find(String.valueOf(book.getIsbn())).getLibraries().size() == 1);
    }
}
