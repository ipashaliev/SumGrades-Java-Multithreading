package executorService;

import tools.CSVReader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class LineProcessThread  implements Runnable{
    private String threadName;
    private CSVReader reader;
    private List<Double> results;

    public LineProcessThread(String threadName, CSVReader reader, List<Double> results) {
        this.threadName = threadName;
        this.reader = reader;
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
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

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
