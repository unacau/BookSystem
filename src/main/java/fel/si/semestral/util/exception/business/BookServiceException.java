package fel.si.semestral.util.exception.business;

import fel.si.semestral.util.exception.BookSystemException;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
public class BookServiceException extends BookSystemException {
    public enum Reason {
        BookDoesNotExist("Book with isbn [%s] does not exist."),
        LibraryDoesNotExist("Library with name [%s] does not exist."),
        LibraryHasMaxNumOfBooks("Library [%s] has already had maximum number of books with isbn [%s]."),
        BookWasNotPublished("Book [%s] was not published.");

        public final String message;

        Reason(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private final Reason reason;

    public BookServiceException(Reason reason, String... messages) {
        super(String.format(reason.getMessage(), (Object[]) messages));
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
