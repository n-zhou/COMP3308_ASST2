import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class KNN {

    private ArrayList<Data> data;
    private int k;

    public KNN(int k) {
        data = new ArrayList<>();
        this.k = k;
    }

    public void train(String train) {
        try {
            Scanner sc = new Scanner(new File(train));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] split = line.split(",");
                data.add(new Data(new double[] {Double.parseDouble(split[0]),
                                    Double.parseDouble(split[1]),
                                    Double.parseDouble(split[2]),
                                    Double.parseDouble(split[3]),
                                    Double.parseDouble(split[4]),
                                    Double.parseDouble(split[5]),
                                    Double.parseDouble(split[6]),
                                    Double.parseDouble(split[7])},
                                    split[8].equals("yes")));
            }
            sc.close();
        } catch (IOException e) {
            throw new InternalError("some idiot managed to do the impossible");
        }
    }

    public void run(String test) {
        try {
            Scanner sc = new Scanner(new File(test));
            while (sc.hasNextLine()) {
                String[] split = sc.nextLine().split(",");
                double[] v = new double[8];
                for (int i = 0; i < 8; ++i) {
                    v[i] = Double.parseDouble(split[i]);
                }
                System.out.println(find(v));
            }
            sc.close();
        } catch(IOException e) {
            throw new InternalError("if you see this message then you're a moron");
        }
    }

    public String find(double[] v) {
        @SuppressWarnings("unchecked") ArrayList<Data> nearestNeighbours = (ArrayList) data.clone();
        nearestNeighbours.sort(new Comparator<Data>() {

            public int compare(Data d1, Data d2) {
                double v1 = 0;
                double v2 = 0;
                for (int i = 0; i < 8; ++i) {
                    v1 += Math.pow(d1.getValue(i) - v[i], 2);
                    v2 += Math.pow(d2.getValue(i) - v[i], 2);
                }
                Math.sqrt(v1);
                Math.sqrt(v2);
                return (v1 <= v2) ? -1 : 1;
            }

        });
        int no_of_yes = 0;
        int no_of_no = 0;
        for(int i = 0; i < k && k < data.size(); ++i) {
            if (nearestNeighbours.get(i).class_()) {
                ++no_of_yes;
            } else {
                ++no_of_no;
            }
        }
        return (no_of_yes >= no_of_no) ? "yes" : "no";
    }

}
