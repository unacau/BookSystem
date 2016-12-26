package fel.si.semestral.persistence.dao;

import java.util.List;

/**
 * Created by nogoltsov on 07.11.2016.
 */
public interface Dao<T> {
    void create(T t);
    void update(T t);
    void delete(Long id);
    List<T> list();
    T find(Long id);
    T find(String param);
}
