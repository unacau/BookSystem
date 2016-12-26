package fel.si.semestral.persistence.dao;

import fel.si.semestral.persistence.model.Publisher;

import javax.persistence.Query;

/**
 * Created by nogoltsov on 07.11.2016.
 */
public class PublisherDaoImpl extends GenericDaoImpl<Publisher> implements Dao<Publisher> {

    @Override
    public Publisher find(String name) {
        Query query = em.createQuery("from Publisher as p where p.name = :name");
        query.setParameter("name", name);
        return (Publisher) query.getSingleResult();
    }
}
