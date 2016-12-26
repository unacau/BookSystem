package fel.si.semestral.util.exception.business;

import fel.si.semestral.util.exception.BookSystemException;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
public class PublisherServiceException extends BookSystemException {
    public enum Reason {
        BookDoesNotExist("Book [%s] does not exist."),
        PublisherDoesNotExist("Publisher with name [%s] does not exist."),
        PublisherHasAlreadyHadAuthor("Publisher [%s] has already had author with name [%s]."),
        BookHasBeenAlreadyPublished("Book [%s] has been already published."),
        BookAuthorHasNotGotContract("Author [%s] has not got contract with publisher[%s]");

        public final String message;

        Reason(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private final Reason reason;

    public PublisherServiceException(Reason reason, String... messages) {
        super(String.format(reason.getMessage(), (Object[]) messages));
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
