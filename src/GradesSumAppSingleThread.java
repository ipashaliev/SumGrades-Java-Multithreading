import tools.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

public class GradesSumAppSingleThread {
    //add MOCK_DATA.csv file path below
    private static final String filePath = "";

    public static void main(String[] args) {
       try {
           long start = LocalDateTime.now().getNano();
           CSVReader reader = new CSVReader(filePath);

           processFileSingleThread(reader);

           long end = LocalDateTime.now().getNano();

           System.out.println("Reading took: " + (end - start)/1000000.0  + " milliseconds.");
       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       }

    }

    static void processFileSingleThread(CSVReader reader) {
        long startTime = LocalDateTime.now().getNano();
        int linesCtr = 0;
        double averageGrade;
        double sumOfGrades = 0.0;
        String[] line;

        try {
            do {
                line = reader.readLine();
                if (line != null && isValidGrade(line[3])) {
                    double currentGrade = Double.parseDouble(line[3]);
                    sumOfGrades += currentGrade;
                    System.out.println(Thread.currentThread().getName() + " adds " + currentGrade);
                    linesCtr++;
                }
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

            }while (line != null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        averageGrade = sumOfGrades/linesCtr;


        long endTime = LocalDateTime.now().getNano();
        System.out.println("Total execution time of thread " + Thread.currentThread().getName() + " was " + (endTime - startTime)/1000000.0 + " milliseconds.");
        System.out.println("Sum from thread " + Thread.currentThread().getName() + " = " + sumOfGrades);
        System.out.println("Calculated average grade from thread " + Thread.currentThread().getName() + " = " + averageGrade);

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
