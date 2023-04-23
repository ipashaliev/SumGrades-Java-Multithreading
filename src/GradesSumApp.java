import threads.LineProcessingThread;
import threads.SumResultThread;
import tools.CSVReader;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class GradesSumApp {
    //add MOCK_DATA.csv file path below
    private static final String filePath = "";
    private static final int threadsCount = 3;
    private static CyclicBarrier barrier;
    private static List<Double> results = new ArrayList<>();

    public static void main(String[] args) {

        try {
            barrier = new CyclicBarrier(threadsCount, new SumResultThread(results));
            long start = LocalDateTime.now().getNano();
            CSVReader reader = new CSVReader(filePath);

            processFileMultithreading(reader);

            long end = LocalDateTime.now().getNano();

            System.out.println("Reading took: " + (end - start)/1000000.0  + " milliseconds.");
            System.out.println("Memory used: "+Runtime.getRuntime().totalMemory() + " bytes");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private static void processFileMultithreading(CSVReader reader) {
        for (int i = 0; i < threadsCount; i++) {
            LineProcessingThread currentThread = new LineProcessingThread("Thread" + i, reader, barrier, results);
            currentThread.start();
        }
    }
}
