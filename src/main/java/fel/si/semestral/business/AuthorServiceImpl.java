package fel.si.semestral.business;

import fel.si.semestral.persistence.dao.Dao;
import fel.si.semestral.persistence.model.Author;
import fel.si.semestral.persistence.model.Publisher;
import fel.si.semestral.util.exception.BookSystemException;
import fel.si.semestral.util.exception.business.AuthorServiceException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import java.util.Comparator;
import java.util.List;

import static fel.si.semestral.util.exception.business.AuthorServiceException.Reason.AuthorDoesNotExist;
import static fel.si.semestral.util.exception.business.AuthorServiceException.Reason.PublisherDoesNotExist;
import static fel.si.semestral.util.exception.business.AuthorServiceException.Reason.PublisherHasAlreadyHadAuthor;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
@Stateless
public class AuthorServiceImpl extends GenericServiceImpl<Author> implements AuthorService{

    @Inject
    private Dao<Publisher> publisherDao;

    @Override
    public List<Author> list() {
        List<Author> res = super.list();
        res.sort(Comparator.comparing(Author::getEmail));
        return res;
    }

    @Override
    public void contractWithPublisher(String authorEmail, String publisherName) {
        Author author = getAuthor(authorEmail);
        Publisher publisher = getPublisher(publisherName);

        if (publisher.getAuthors() != null && publisher.getAuthors().contains(author)) {
                BookSystemException exception =
                    new AuthorServiceException(PublisherHasAlreadyHadAuthor, publisherName, authorEmail);
            logger.warn(exception.getMessage());
            throw exception;
        }

        author.addPublisher(publisher);
        publisher.addAuthor(author);
        update(author);
        logger.info("Updated author's[{}] publishers list with publisher[{}].", author.getEmail(), publisher.getName());
        publisherDao.update(publisher);
        logger.info("Updated publisher's[{}] authors list with author[{}].", publisher.getName(), author.getName());
    }

    private Author getAuthor(String authorEmail) {
        try {
            return dao.find(authorEmail);
        } catch (NoResultException ex) {
            BookSystemException exception = new AuthorServiceException(AuthorDoesNotExist, authorEmail);
            logger.warn(exception.getMessage());
            throw exception;
        }
    }

    private Publisher getPublisher(String publisherName) {
        try {
            return publisherDao.find(publisherName);
        } catch (NoResultException ex) {
            BookSystemException exception = new AuthorServiceException(PublisherDoesNotExist, publisherName);
            logger.warn(exception.getMessage());
            throw exception;
        }
    }
}
