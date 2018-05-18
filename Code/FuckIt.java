import java.util.*;

public class FuckIt {


    public static LinkedList<String> init() throws Exception {
        LinkedList<String> correct = new LinkedList<>();
        Scanner sc = new Scanner(new java.io.File("pima-folds.csv"));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.contains("fold") || line.isEmpty())
                 continue;
            correct.add(line.split(",")[line.split(",").length-1]);
        }
        return correct;
    }

    public static void main(String[] args) throws Exception {
        LinkedList<String> cor = init();
        LinkedList<Double> accuracy = new LinkedList<>();
        Scanner sc = new Scanner(new java.io.File("work"));
        for (int i = 0; i < 8; ++i) {
            int correct = 0;
            for (int j = 0; j < 77; j++) {
                String line = sc.nextLine();
                String[] split = line.split(",");
                String clas = split[split.length-1];
                if (clas.equals(cor.get(i*77+j)))
                    ++correct;
            }
            accuracy.add((double)correct/77);
        }
        for (int i = 0; i < 2; ++i) {
            int correct = 0;
            for (int j = 0; j < 76; j++) {
                String line = sc.nextLine();
                String[] split = line.split(",");
                String clas = split[split.length-1];
                if (clas.equals(cor.get(i*76+j)))
                    ++correct;
            }
            accuracy.add((double)correct/76);
        }
        double d = 0;
        for (int i = 0; i < accuracy.size(); i++) {
            d += accuracy.get(i);
        }
        System.out.printf("%.4f\n", d*100/10);

    }

}
