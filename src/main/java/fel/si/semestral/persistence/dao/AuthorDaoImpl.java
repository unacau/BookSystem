package fel.si.semestral.persistence.dao;

import fel.si.semestral.persistence.model.Author;

import javax.persistence.Query;


/**
 * Created by Nikolai Ogoltsov on 11/20/2016.
 */
public class AuthorDaoImpl extends GenericDaoImpl<Author> implements Dao<Author> {
    @Override
    public Author find(String email) {
        Query query = em.createQuery("from Author where email = :email");
        query.setParameter("email", email);
        return (Author) query.getSingleResult();
    }
}
