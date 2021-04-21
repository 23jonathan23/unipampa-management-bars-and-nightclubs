package edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Interfaces.IDBRepository;

import java.nio.file.*;

@SuppressWarnings("unchecked")
public class DBRepository<T> implements IDBRepository<T>{
    private Path _pathDB;
    private FileOutputStream _fileOutStream;
    private ObjectOutputStream _objectOutStream;
    private FileInputStream _fileInputStream;
    private ObjectInputStream _objectInputStream;
    private List<T> _cache;
    private boolean _updateCache = true;

    public DBRepository(Path pathBD) {
        _pathDB = pathBD;
    }

    public void insert(T entity) throws IOException, ClassNotFoundException, IllegalArgumentException {
        List<T> listEntity = new ArrayList<>();

        if (Files.exists(_pathDB)) {
            openFileRead(_pathDB.toString());

            listEntity = (List<T>) _objectInputStream.readObject();

            disposeFileRead();

            for (var entityOld : listEntity) {
                if (entityOld.equals(entity)) {
                    throw new IllegalArgumentException("O registro informado já existe");
                }
            }
        }

        openFileWrite(_pathDB.toString());

        listEntity.add(entity);

        _objectOutStream.writeObject(listEntity);

        disposeFileWrite();
        
        _updateCache = true;
    }

    public void update(T entity) throws IOException, ClassNotFoundException, IllegalArgumentException {
        var listEntity = queryAll();

        for (var entityOld : listEntity) {
            if (entityOld.equals(entity)) {
                listEntity.set(listEntity.indexOf(entityOld), entity);

                openFileWrite(_pathDB.toString());

                _objectOutStream.writeObject(listEntity);

                disposeFileWrite();

                return;
            }
        }

        throw new IllegalArgumentException("O registro informado não existe");
    }

    public void delete(T entity) throws IOException, ClassNotFoundException, IllegalArgumentException {
        var listEntity = queryAll();

        for (var entityOld : listEntity) {
            if (entityOld.equals(entity)) {
                listEntity.remove(listEntity.indexOf(entity));

                openFileWrite(_pathDB.toString());

                _objectOutStream.writeObject(listEntity);

                disposeFileWrite();

                return;
            }
        }

        throw new IllegalArgumentException("O registro informado não existe");
    }

    public List<T> queryAll() throws IOException, ClassNotFoundException {
        if (_updateCache) {
            openFileRead(_pathDB.toString());
    
            var listEntity = (List<T>) _objectInputStream.readObject();
    
            disposeFileRead();

            _updateCache = false;
    
            return listEntity;
        }

        return _cache;
    }

    private void openFileWrite(String file) throws IOException {
        _fileOutStream = new FileOutputStream(file);
        _objectOutStream = new ObjectOutputStream(_fileOutStream);
    }

    private void disposeFileWrite() throws IOException {
        _fileOutStream.close();
        _objectOutStream.close();
    }

    private void openFileRead(String file) throws IOException {
        _fileInputStream = new FileInputStream(file);
        _objectInputStream = new ObjectInputStream(_fileInputStream);
    }

    private void disposeFileRead() throws IOException {
        _fileInputStream.close();
        _objectInputStream.close();
    }
}