package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader implements AutoCloseable{
    BufferedReader reader;
    public CSVReader(String filePath) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filePath));
    }

    public synchronized String[] readLine() throws IOException {
       String line = reader.readLine();
       if (line != null){
           String[] result = line.split(",");
           return result;
       }
       return null;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
