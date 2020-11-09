package pss.library;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class SerializableObjectReader<T> {
    protected final String fileName;

    public SerializableObjectReader(String fileName) {
        this.fileName = fileName;
    }

    public List<T> getObjects(int totalCount) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        List<T> objects = new ArrayList<T>();
        for (int i = 0; i < totalCount; i++) {
            T object = (T) objectInputStream.readObject();
            objects.add(object);
        }
        objectInputStream.close();
        fileInputStream.close();
        return objects;
    }

    public T getObject() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        T object = (T) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();
        return object;
    }
}
