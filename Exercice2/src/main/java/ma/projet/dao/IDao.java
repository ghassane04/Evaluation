package ma.projet.dao;

import java.util.List;

public interface IDao<T> {
    void create(T t);
    T findById(int id);
    List<T> findAll();
    void update(T t);
    void delete(T t);
}
