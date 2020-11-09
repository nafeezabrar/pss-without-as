package pss.reader.text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileReader {
    protected final String fileName;

    public TextFileReader(String fileName) {
        this.fileName = fileName;
    }

    public List<Double> readDoubles() throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        List<Double> doubles = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            doubles.add(scanner.nextDouble());
        }
        return doubles;
    }

    public List<Double> readDoubles(int ignoreCount) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        for (int i = 0; i < ignoreCount; i++) {
            scanner.next();
        }
        List<Double> doubles = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            doubles.add(scanner.nextDouble());
        }
        return doubles;
    }
}
