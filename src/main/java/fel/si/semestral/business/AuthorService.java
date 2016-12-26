package fel.si.semestral.business;

import fel.si.semestral.persistence.model.Author;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
public interface AuthorService extends Service<Author> {

    void contractWithPublisher(String authorEmail, String publisherName);
}
