package edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;

@SuppressWarnings("unchecked")
public abstract class DBBaseRepository<T> {
    protected final int INDEX_DIFF = 1;
    protected Path _pathDB;
    protected FileOutputStream _fileOutStream;
    protected ObjectOutputStream _objectOutStream;
    protected FileInputStream _fileInputStream;
    protected ObjectInputStream _objectInputStream;
    protected List<T> _cache;
    protected boolean _updateCache = true;

    public DBBaseRepository(Path pathBD) {
        _pathDB = pathBD;
    }

    public void insert(T entity) throws IOException, ClassNotFoundException {
        List<T> listEntity = new ArrayList<>();

        if (Files.exists(_pathDB)) {
            openFileRead(_pathDB.toString());

            listEntity = (List<T>) _objectInputStream.readObject();

            disposeFileRead();
        }

        openFileWrite(_pathDB.toString());

        int lastId = listEntity.isEmpty()
            ? listEntity.size() 
            : listEntity.get(listEntity.size() - INDEX_DIFF).getId();

        entity.setId(lastId + INDEX_DIFF);

        listEntity.add(entity);

        _objectOutStream.writeObject(listEntity);

        disposeFileWrite();
        
        _updateCache = true;
    }

    public void update(T entity) throws IOException, ClassNotFoundException {
        var listEntity = queryAll();

        for (var entityOld : listEntity) {
            if (entityOld.getId() == entity.getId()) {
                listEntity.set(listEntity.indexOf(entityOld), entity);
                break;
            }
        }

        openFileWrite(_pathDB.toString());

        _objectOutStream.writeObject(listEntity);

        disposeFileWrite();
    }

    public void delete(int id) throws IOException, ClassNotFoundException {
        var listEntity = queryAll();

        for (var entity : listEntity) {
            if (entity.getId() == id) {
                listEntity.remove(listEntity.indexOf(entity));
                break;
            }
        }

        openFileWrite(_pathDB.toString());

        _objectOutStream.writeObject(listEntity);

        disposeFileWrite();
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