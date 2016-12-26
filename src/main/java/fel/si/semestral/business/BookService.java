package fel.si.semestral.business;

import fel.si.semestral.persistence.model.Book;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
public interface BookService extends Service<Book> {
    void addBookToLibrary(long bookIsbn, String libraryName);
}
