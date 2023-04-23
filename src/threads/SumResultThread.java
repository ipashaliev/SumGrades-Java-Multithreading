package threads;
import java.util.List;

public class SumResultThread extends Thread{
    private List<Double> results;

    public SumResultThread(List<Double> results) {
        this.results = results;
    }

    @Override
    public void run() {
        System.out.println("Sum result thread started...");
        double sum = 0.0;
        double averageGrade;
        for (double grade:results) {
            sum += grade;
        }
        averageGrade = sum/results.size();
        System.out.println("Average grade: " + averageGrade);
    }
}
