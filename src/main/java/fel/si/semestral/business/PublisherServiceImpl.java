package fel.si.semestral.business;

import fel.si.semestral.persistence.dao.Dao;
import fel.si.semestral.persistence.model.Author;
import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Publisher;
import fel.si.semestral.util.exception.BookSystemException;
import fel.si.semestral.util.exception.business.PublisherServiceException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import java.util.Comparator;
import java.util.List;

import static fel.si.semestral.util.exception.business.PublisherServiceException.Reason.*;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
@Stateless
public class PublisherServiceImpl extends GenericServiceImpl<Publisher> implements PublisherService {

    @Inject
    private Dao<Book> bookDao;

    @Override
    public List<Publisher> list() {
        List<Publisher> res = super.list();
        res.sort(Comparator.comparing(Publisher::getName));
        return res;
    }

    @Override
    public void publishBook(String publisherName, long bookIsbn) {
        Publisher publisher = getPublisher(publisherName);
        Book book = getBook(bookIsbn);

        if (book.getPublisher() != null) {
            BookSystemException exception = new PublisherServiceException(BookHasBeenAlreadyPublished, book.getTitle());
            logger.warn(exception.getMessage());
            throw exception;
        }

        if (book.getAuthors() != null) {
            List<Author> authorList = publisher.getAuthors();
            book.getAuthors().forEach(author -> {
                if (authorList == null || !authorList.contains(author)) {
                    BookSystemException exception = new PublisherServiceException(
                            BookAuthorHasNotGotContract, author.getEmail(), publisherName
                    );
                    logger.warn(exception.getMessage());
                    throw exception;
                }
            });
        }
        book.setPublisher(publisher);
        logger.info("Updated book's[{}] publishers list with publisher[{}].", book.getIsbn(), publisher.getName());
        publisher.addBook(book);
        logger.info("Updated publisher's[{}] books list with book[{}].", publisher.getName(), book.getIsbn());

        update(publisher);
        bookDao.update(book);
    }

    private Publisher getPublisher(String publisherName) {
        try {
            return dao.find(publisherName);
        } catch (NoResultException ex) {
            BookSystemException exception = new PublisherServiceException(PublisherDoesNotExist, publisherName);
            logger.warn(exception.getMessage());
            throw exception;
        }
    }

    private Book getBook(long bookIsbn) {
        try {
            return bookDao.find(String.valueOf(bookIsbn));
        } catch (NoResultException ex) {
            BookSystemException exception = new PublisherServiceException(BookDoesNotExist, String.valueOf(bookIsbn));
            logger.warn(exception.getMessage());
            throw exception;
        }
    }
}
