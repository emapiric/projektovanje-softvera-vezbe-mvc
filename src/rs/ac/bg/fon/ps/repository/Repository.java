package rs.ac.bg.fon.ps.repository;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Product;

/**
 *
 * @author laptop-02
 * @param <T>
 */
public interface Repository<T> {
    List<T> getAll();
    void add(T param) throws Exception;
    void edit(T param) throws Exception;
    void delete(T param)throws Exception;
}
