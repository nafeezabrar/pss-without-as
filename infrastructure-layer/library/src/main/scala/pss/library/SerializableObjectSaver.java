package pss.library;

import java.io.*;
import java.util.List;

public class SerializableObjectSaver<T> {
    protected final String fileName;

    public SerializableObjectSaver(String fileName) {
        this.fileName = fileName;
    }

    public void saveObject(T object) throws IOException, ClassNotFoundException {
        File file = new File(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        fileOutputStream.close();
    }

    private int readCount() throws IOException {

        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        int count = objectInputStream.readInt();
//        objectInputStream.close();
//        fileInputStream.close();
        return count;
    }

    public List<T> readObjects(int count) throws IOException, ClassNotFoundException {
        SerializableObjectReader objectReader = new SerializableObjectReader(fileName);
        List<T> objects = objectReader.getObjects(count);
        return objects;
    }

    class MyObjectOutputStream extends ObjectOutputStream {

        public MyObjectOutputStream(OutputStream os) throws IOException {
            super(os);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
}

class AppendingObjectOutputStream extends ObjectOutputStream {

    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // do not write a header, but reset:
        // this line added after another question
        // showed a problem with the original
        reset();
    }

}
