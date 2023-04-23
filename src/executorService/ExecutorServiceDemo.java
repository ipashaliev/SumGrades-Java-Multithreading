package executorService;

import threads.SumResultThread;
import tools.CSVReader;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceDemo {
    //add MOCK_DATA.csv file path below
    private static final String filePath = "";

    public static void main(String[] args) {
       try {
           long start = LocalDateTime.now().getNano();
           CSVReader reader = new CSVReader(filePath);
           List<Double> results = new ArrayList<>();
           List<Future<?>> futures = new ArrayList<>();

           ExecutorService executor = Executors.newFixedThreadPool(3);
           for (int i = 0; i < 3; i++) {
               Future<?> future = executor.submit(new LineProcessThread("Thread" + i, reader, results));
               futures.add(future);
           }

           for (Future<?> f : futures) {
               f.get();
           }

           executor.shutdown();

           System.out.println("All threads finished execution");
           new Thread(new SumThread(results)).start();

           long end = LocalDateTime.now().getNano();

           System.out.println("Reading took: " + (end - start)/1000000.0  + " milliseconds.");
       } catch (FileNotFoundException | ExecutionException e) {
           throw new RuntimeException(e);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }

    }


}
