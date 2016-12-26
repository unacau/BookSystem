package fel.si.semestral.persistence.dao;

import fel.si.semestral.persistence.model.Library;

import javax.persistence.Query;

/**
 * Created by nogoltsov on 07.11.2016.
 */
public class LibraryDaoImpl extends GenericDaoImpl<Library> implements Dao<Library> {
    @Override
    public Library find(String name) {
        Query query = em.createQuery("from Library as l where l.name = :name");
        query.setParameter("name", name);
        return (Library) query.getSingleResult();
    }
}
