package br.com.clepsidra.core.interfaces;

import java.util.List;

public interface Controller<T, Z, O> {
    T insert(Z dto);
    T find(Long id);
    void delete(Long id);
    T update(Long id, Z dto);
    List<T> findAll();
    List<T> findPage(int page);
    T findByParams(Z param);
    T remap(O obj);

}
