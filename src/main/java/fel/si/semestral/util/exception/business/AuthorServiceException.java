package fel.si.semestral.util.exception.business;

import fel.si.semestral.util.exception.BookSystemException;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
public class AuthorServiceException extends BookSystemException {

    public enum Reason {
        PublisherDoesNotExist("Publisher with name [%s] does not exist."),
        AuthorDoesNotExist("Author with email [%s] does not exist."),
        PublisherHasAlreadyHadAuthor("Publisher [%s] has already had author with email [%s].");

        public final String message;

        Reason(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private final Reason reason;

    public AuthorServiceException(Reason reason, String... messages) {
        super(String.format(reason.getMessage(), (Object[]) messages));
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
