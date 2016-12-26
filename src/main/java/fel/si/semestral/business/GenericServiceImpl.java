package fel.si.semestral.business;

import fel.si.semestral.persistence.dao.Dao;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
public abstract class GenericServiceImpl<T> implements Service<T> {

    @Inject
    protected Logger logger;

    @Inject
    protected Dao<T> dao;

    @Override
    public void create(T t) {
        dao.create(t);
    }

    @Override
    public void update(T t) {
        dao.update(t);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public List<T> list() {
        return dao.list();
    }

    @Override
    public T find(Long id) {
        return dao.find(id);
    }

    @Override
    public T find(String param) {
        return dao.find(param);
    }
}
