package fel.si.semestral.persistence.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by nogoltsov on 07.11.2016.
 */
public abstract class GenericDaoImpl<T> implements Dao<T> {

    @Inject
    protected EntityManager em;
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public void create(T t) {
        em.persist(t);
    }

    @Override
    public void update(T t) {
        em.merge(t);
    }

    @Override
    public void delete(Long id) {
        T t = find(id);
        if (t != null) {
            em.remove(t);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> list() {
        Query query = em.createQuery("select a from " + type.getSimpleName() + " a");
        return (List<T>)query.getResultList();
    }

    @Override
    public T find(Long id) {
        return em.find(type, id);
    }
}
