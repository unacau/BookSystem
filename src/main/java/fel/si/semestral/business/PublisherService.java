package fel.si.semestral.business;

import fel.si.semestral.persistence.model.Publisher;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
public interface PublisherService extends Service<Publisher> {
    void publishBook(String publisherName, long bookIsbn);
}
