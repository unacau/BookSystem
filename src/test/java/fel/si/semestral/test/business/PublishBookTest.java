package fel.si.semestral.test.business;

import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Library;
import fel.si.semestral.persistence.model.Publisher;
import fel.si.semestral.util.exception.business.BookServiceException;
import fel.si.semestral.util.exception.business.PublisherServiceException;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import javax.ejb.EJBTransactionRolledbackException;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PublishBookTest extends ServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test1PublisherDoesNotExist() {
        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Publisher with name [1234232] does not exist.");
        publisherService.publishBook("1234232", 12345);
    }

    @Test
    public void test2BookDoesNotExist() {
        Publisher publisher = publishers.get(0);
        publisherService.create(publisher);

        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Book [12345] does not exist.");
        publisherService.publishBook(publisher.getName(), 12345);
    }

    @Test
    public void test3BookHasAlreadyHadPublisher() {
        Publisher publisher0 = publishers.get(0);
        Publisher publisher1 = publishers.get(1);
        Book book = books.get(0);
        book.setPublisher(publisher0);

        publisherService.create(publisher0);
        publisherService.create(publisher1);
        bookService.create(book);

        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Book [title100] has been already published.");
        publisherService.publishBook(publisher1.getName(), book.getIsbn());
    }

    @Test
    public void test4Publish() {
        Book book = books.get(2);
        Publisher publisher = publishers.get(1);

        book.getAuthors().forEach(author -> {
            author.addPublisher(publisher);
            authorService.update(author);
        });
        bookService.create(book);
        publisherService.create(publisher);

        publisherService.publishBook(publisher.getName(), book.getIsbn());

        assertTrue(bookService.find(book.getIsbn() + "").getPublisher() != null);
        assertTrue(publisherService.find(publisher.getName()).getBooks().size() == 1);
    }
}
