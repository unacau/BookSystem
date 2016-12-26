package fel.si.semestral.business;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Nikolai Ogoltsov on 12/18/2016.
 */
@Local
public interface Service<T> {
    void create(T t);
    void update(T t);
    void delete(Long id);
    List<T> list();
    T find(Long id);
    T find(String param);
}
