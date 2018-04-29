import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class KNN {

    private ArrayList<Data> data;
    private int k;

    /**
     * initialises the class to search the k nearest neighbours.
     * @param k The maximum number of neighbours we are going to search.
     */
    public KNN(int k) {
        data = new ArrayList<>();
        this.k = k;
    }

    /**
     * This method reads in the training file and processes the data.
     * @param train train is the filename of the training file.
     */
    public void train(String train) {
        try {
            Scanner sc = new Scanner(new File(train));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] split = line.split(",");
                double[] values = new double[split.length - 1];
                for (int i = 0; i < values.length; ++i) {
                    values[i] = Double.parseDouble(split[i]);
                }
                data.add(new Data(values, split[split.length - 1].equals("yes")));
            }
            sc.close();
        } catch (IOException e) {
            throw new InternalError("some idiot managed to do the impossible");
        }
    }

    /**
     * the test file is processed and the result yes/no is printed to standard output.
     * @param test is the filename of the testing file the knn algorithm is attempting
     * to predict the value of.
     */
    public void run(String test) {
        try {
            Scanner sc = new Scanner(new File(test));
            while (sc.hasNextLine()) {
                String[] split = sc.nextLine().split(",");
                double[] v = new double[split.length];
                for (int i = 0; i < v.length; ++i) {
                    v[i] = Double.parseDouble(split[i]);
                }
                System.out.println(find(v));
            }
            sc.close();
        } catch(IOException e) {
            throw new InternalError("if you see this message then you're a moron");
        }
    }

    /**
     * The method evaluates the nearest neighbours of v and returns yes/no
     * depending on the concensus reached.
     * @param  v A double array of length 8 that contains the values of the attributes we need.
     * @return   yes if the number of yes' is greater than the number of no's.
     */
    public String find(double[] v) {
        @SuppressWarnings("unchecked") ArrayList<Data> nearestNeighbours = (ArrayList) data.clone();
        nearestNeighbours.sort(new Comparator<Data>() {

            public int compare(Data d1, Data d2) {
                double v1 = 0;
                double v2 = 0;
                for (int i = 0; i < d1.getValues().length; ++i) {
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
        for(int i = 0; (i < k) && (k < data.size()); ++i) {
            if (nearestNeighbours.get(i).class_()) {
                ++no_of_yes;
            } else {
                ++no_of_no;
            }
        }
        return (no_of_yes >= no_of_no) ? "yes" : "no";
    }

}
