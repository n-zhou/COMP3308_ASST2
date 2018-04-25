import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class NaiveBayes {

    private ArrayList<Data> data;
    private double p_yes; // p(yes) -> yes / data.size();
    private double p_no; // p(no) -> no / data.size();
    private int yes; // number of yes outcomes
    private int no; // number of no outcomes

    /** XXX
     * This constuctor initialises the member variables to their default values.
     */
    public NaiveBayes() {
        data = new ArrayList<>();
        p_yes = 0;
        p_no = 0;
        yes = 0;
        no = 0;
    }

    /** XXX
     * This method reads and processes the training file. The probability of the p(no)
     * and p(yes) is calculated in this method.
     * @param trainingFile This should be args[0] from the main method. This parameter
     * is the name of the file we are going to read in the training data from.
     */
    public void train(String trainingFile) {
        try {
            Scanner sc = new Scanner(new File(trainingFile));
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
            for (Data d : data) {
                int i = d.class_() ? ++yes : ++no;
            }
            p_yes = (double) yes / data.size();
            p_no = (double) no / data.size();

        } catch (IOException e) {
            //we should never reach this block of code
            throw new InternalError("Some moron managed to reach this block of code");
        }
    }

    /** XXX
     * Finds the mean of a given attribute and certain outcome.
     * @param  index   index of the attribute we want to find the mean of.
     * The values must be between [0, 7]
     * @param  outcome true for class yes, false for class no.
     * @return         returns the mean calculated.
     */
    public double getMean(int index, boolean outcome) {
        double i = 0;
        for(Data d : data) {
            if (outcome && d.class_()) {
                i += d.getValue(index);
            } else if (!outcome && !d.class_()) {
                i += d.getValue(index);
            }
        }
        return (outcome) ? i / yes : i / no;
    }


    /** XXX
     * This method is used to find the standard deviation of a particular attribute,
     * given an outcome.
     * @param  index   index of the attribute we want to find the standard deviation of.
     * The values must be between [0, 7].
     * @param  outcome true for class yes, false for class no.
     * @return         returns the standard deviation calculated.
     */
    public double getStdDeviation(int index, boolean outcome) {
        double i = 0;
        double u = getMean(index, outcome);
        for(Data d : data) {
            if (outcome && d.class_()) {
                i += Math.pow(d.getValue(index) - u, 2);
            } else if (!outcome && !d.class_()) {
                i += Math.pow(d.getValue(index) - u, 2);
            }
        }
        return (outcome) ? Math.sqrt(i / yes) : Math.sqrt(i / no);
    }

    /** XXX
     * The method processes the data given in the file, whose name is specified
     * by the parameter. It will attempt to predict whether the class outcome is
     * yes or no and prints it to standard output.
     * @param test is the file name of the file we are going to test.
     */
    public void run(String test) {
        try {
            Scanner sc = new Scanner(new File(test));
            while (sc.hasNextLine()) {
                String[] split = sc.nextLine().split(",");
                double[] v = new double[8];
                for (int i = 0; i < 8; ++i) {
                    v[i] = Double.parseDouble(split[i]);
                }
                double pYes = p_yes;
                double pNo = p_no;
                for (int i = 0; i < 8; i++) {
                    pYes *= pdf(v[i], getMean(i, true), getStdDeviation(i, true));
                    pNo *= pdf(v[i], getMean(i, false), getStdDeviation(i, false));
                }
                System.out.println((pNo > pYes) ? "no": "yes");
            }
            sc.close();
        } catch (IOException e) {
            //we shouldn't get an exception so the code should never reach this block
        }
    }


    /** XXX
     * This method applies the probability distribution function to the values
     * x, u, and sigma.
     * @param  x     The value we are applying the function to.
     * @param  u     The average.
     * @param  sigma The standard deviation.
     * @return       The result of applying the probability distribution distribution
     *  function to x given u(the average) and sigma(the standard deviation).
     */
    public static double pdf(double x, double u, double sigma) {
        return ((1.0d) / (sigma * Math.sqrt(2 * Math.PI))) *
                Math.exp(-(Math.pow((x - u), 2) / (2 * Math.pow(sigma, 2))));
    }

    /** XXX
     * This method calculates the average of the values at a given index.
     * @param  index Is the index of the values we are using for to find the mean.
     * @return       The mean of the data in the training set.
     */
    public double getMean(int index) {
        double i = 0;
        for(Data d : data) {
            i += d.getValue(index);
        }
        return i / data.size();
    }

    /** XXX
     * calculates the standard deviation of the given index.
     * @param  index The index of values for which we want to find the standard deviation.
     * must be between [0, 7]. Otherwise undefined behaviour results.
     * Undefined behaviour results if the index is not valid.
     * @return       returns the standard deviation for the index we desire.
     */
    public double getStdDeviation(int index) {
        double i = 0;
        double u = getMean(index);
        for(Data d : data) {
            i += Math.pow(d.getValue(index) - u, 2);
        }
        return Math.sqrt(i / data.size());
    }

}
