package edu.unipampa.poo.management.bars.and.nightclubs.Infra.Interfaces;

import java.io.IOException;
import java.util.List;

public interface IDBRepository<T> {
    void insert(T entity) throws IOException, ClassNotFoundException, IllegalArgumentException;
    void update(T entity) throws IOException, ClassNotFoundException, IllegalArgumentException;
    void delete(T entity) throws IOException, ClassNotFoundException, IllegalArgumentException;
    List<T> queryAll() throws IOException, ClassNotFoundException;
}
