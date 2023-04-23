package threads;
import tools.CSVReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LineProcessingThread extends Thread{
    private String threadName;
    private CSVReader reader;
    private CyclicBarrier barrier;
    private List<Double> results;

    public LineProcessingThread(String threadName, CSVReader reader, CyclicBarrier barrier, List<Double> results) {
        this.threadName = threadName;
        this.reader = reader;
        this.barrier = barrier;
        this.results = results;
    }

    @Override
    public void run() {
        long startTime = LocalDateTime.now().getNano();
        int linesCtr = 0;
        double averageGrade = 0.0;
        double sumOfGrades = 0.0;
        String[] line;

        try {
            do {
                line = reader.readLine();
                if (line != null && isValidGrade(line[3])) {
                    double currentGrade = Double.parseDouble(line[3]);
                    sumOfGrades += currentGrade;
                    linesCtr++;
                    System.out.println(threadName + " adds " + currentGrade);
                }
//                try {
//                    Thread.sleep(4);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

            }while (line != null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        averageGrade = sumOfGrades/linesCtr;

        results.add(averageGrade);

        long endTime = LocalDateTime.now().getNano();
        System.out.println("Total execution time of thread " + threadName + " was " + (endTime - startTime)/1000000.0 + " milliseconds.");
        System.out.println("Sum from thread " + threadName + " = " + sumOfGrades);
        System.out.println("Calculated average grade from thread " + threadName + " = " + averageGrade);

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean isValidGrade(String grade){
        if (grade == null) {
            return false;
        }
        try {
            Double.parseDouble(grade);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
