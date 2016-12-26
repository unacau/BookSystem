package fel.si.semestral.test.business;


import fel.si.semestral.persistence.model.Author;
import fel.si.semestral.persistence.model.Publisher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import javax.ejb.EJBTransactionRolledbackException;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContractWithPublisherTest extends ServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test1AuthorDoesNotExist() {
        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Author with email [12345] does not exist.");
        authorService.contractWithPublisher("12345", "1234");
    }

    @Test
    public void test2PublisherDoesNotExist() {
        Author author = authors.get(0);
        authorService.create(author);

        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Publisher with name [blahblahblah] does not exist.");
        authorService.contractWithPublisher(author.getEmail(), "blahblahblah");
    }

    @Test
    public void test3PublisherHasAlreadyHadAuthor() {
        Author author = authors.get(1);
        Publisher publisher = publishers.get(0);
        List<Author> pubAut = Collections.singletonList(author);
        publisher.setAuthors(pubAut);

        authorService.create(author);
        publisherService.create(publisher);

        exception.expect(EJBTransactionRolledbackException.class);
        exception.expectMessage("Publisher [name100] has already had author with email [email101@gmail.com].");
        authorService.contractWithPublisher(author.getEmail(), publisher.getName());
    }

    @Test
    public void test4Contract() {
        Author author = authors.get(2);
        Publisher publisher = publishers.get(1);
        authorService.create(author);
        publisherService.create(publisher);

        authorService.contractWithPublisher(author.getEmail(), publisher.getName());

        assertTrue(publisherService.find(publisher.getName()).getAuthors().size() == 1);
        assertTrue(authorService.find(author.getEmail()).getPublishers().size() == 1);
    }
}
