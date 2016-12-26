package fel.si.semestral.business;

import fel.si.semestral.persistence.dao.Dao;
import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Library;
import fel.si.semestral.util.exception.BookSystemException;
import fel.si.semestral.util.exception.business.BookServiceException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import java.util.Comparator;
import java.util.List;

import static fel.si.semestral.util.exception.business.BookServiceException.Reason.*;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
@Stateless
public class BookServiceImpl extends GenericServiceImpl<Book> implements BookService {

    private final static int BOOKS_MAX_NUMBER = 5;

    @Inject
    private Dao<Library> libraryDao;

    @Override
    public List<Book> list() {
        List<Book> res = super.list();
        res.sort(Comparator.comparing(Book::getIsbn));
        return res;
    }

    @Override
    public void addBookToLibrary(long bookIsbn, String libraryName) {
        Book book = getBook(bookIsbn);
        Library library = getLibrary(libraryName);

        if (book.getPublisher() == null) {
            BookSystemException exception = new BookServiceException(BookWasNotPublished, book.getTitle());
            logger.warn(exception.getMessage());
            throw exception;
        }

        if (library.getBooks() != null) {
            long counter = library.getBooks()
                    .stream().filter(book1 -> book1.getIsbn() == bookIsbn).count();
            if (counter >= BOOKS_MAX_NUMBER) {
                BookSystemException exception = new BookServiceException(LibraryHasMaxNumOfBooks, libraryName, String.valueOf(bookIsbn));
                logger.warn(exception.getMessage());
                throw exception;
            }
        }
        book.addLibrary(library);
        library.addBook(book);

        update(book);
        logger.info("Updated book's[{}] libraries list with library[{}].", String.valueOf(bookIsbn), library.getName());
        libraryDao.update(library);
        logger.info("Updated library's[{}] books list with book[{}].", library.getName(), book.getIsbn());
    }

    private Book getBook(long isbn) {
        try {
            return dao.find(String.valueOf(isbn));
        } catch (NoResultException ex) {
            BookSystemException exception = new BookServiceException(BookDoesNotExist, String.valueOf(isbn));
            logger.warn(exception.getMessage());
            throw exception;
        }
    }

    private Library getLibrary(String libraryName) {
        try {
            return libraryDao.find(libraryName);
        } catch (NoResultException ex) {
            BookSystemException exception = new BookServiceException(LibraryDoesNotExist, libraryName);
            logger.warn(exception.getMessage());
            throw exception;
        }
    }
}
