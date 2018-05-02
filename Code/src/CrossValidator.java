import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Collections;

public class CrossValidator {

    private static final int N_FOLDS = 10;
    private static final PrintStream stdOut = System.out;

    String classifier;
    HashMap<String, String> pair;
    LinkedList<String> all;
    LinkedList<LinkedList<String>> folds;



    /**
     * Constructor takes in an argument of which classifier to perform 10 folds
     * stratified cross validation on.
     * @param classifier is either kNN or NB. Otherwise an exception will be
     * thrown. Well probably not.
     */
    public CrossValidator(String classifier) {
        this.classifier = classifier;
        all = new LinkedList<>();
        pair = new HashMap<>();
        folds = new LinkedList<>();
        for (int i = 0; i < N_FOLDS; i++) {
            folds.add(new LinkedList<>());
        }
    }

    /**
     * train initialises the object to be ready for classifying.
     * @param fileName name of the file we are going to perform the cross
     * validation on.
     */
    public void train(String fileName) {
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] split = line.split(",");
                pair.put(keyThis(split), split[split.length - 1]);
                all.add(line);
            }
            makeFolds();

        } catch (IOException e) {

        }
    }

    /**
     * Something about converting a string array into a string.
     * @param  line
     * @return      a suitable hashmap key from the line.
     */
    private String keyThis(String[] line) {
        String s = "";
        for (int i = 0; i < line.length - 1; i++) {
            s += (i < line.length - 2) ?
                String.format("%s,", line[i]) : String.format("%s", line[i]);
        }
        return s;
    }


    @SuppressWarnings("unchecked")
    /**
     * makeFolds makes 10 stratified folds
     */
    private void makeFolds() {
       LinkedList<String> no = new LinkedList<>();
       LinkedList<String> yes = new LinkedList<>();
       for (String s : all) {
           if (s.split(",")[s.split(",").length - 1].equals("yes")) {
               yes.add(s);
           } else {
               no.add(s);
           }
       }
       Random r1 = new Random(1);
       Random r2 = new Random(1);
       Collections.shuffle(no, r1);
       Collections.shuffle(yes, r1);
       int k = 0;
       while (!no.isEmpty()) {
           folds.get(k++ % 10).add(no.poll());
       }
       while (!yes.isEmpty()) {
           folds.get(k++ % 10).add(yes.poll());
       }
    }

    public void crossValidate() {

        File train = new File("train");
        File test = new File("test");
        File work = new File("work");
        try {
            work.delete();
            work.createNewFile();
            System.setOut(new PrintStream(work));

            for (int i = 0; i < N_FOLDS; ++i) {
                train.delete();
                test.delete();
                train.createNewFile();
                test.createNewFile();
                PrintWriter pw = new PrintWriter(train);
                for (int j = 0; j < N_FOLDS; ++j) {
                    LinkedList<String> append = folds.get(j);
                    if (i == j) {
                        PrintWriter testPrinter = new PrintWriter(test);
                        for (String s : append) {
                            testPrinter.println(keyThis(s.split(",")));
                            testPrinter.flush();
                        }
                        continue;
                    }
                    for (String s : append) {
                        pw.println(s);
                        pw.flush();
                    }
                }
                run();
            }
            //resets System output
            System.setOut(stdOut);
            //printResults(work);
        } catch (IOException e) {

        }


    }

    private void run() {
        switch (classifier) {
            case "NB":
                NaiveBayes nb = new NaiveBayes();
                nb.train("train");
                nb.run("test");
                break;
            default:
                int k = classifier.charAt(0) - '0';
                KNN knn = new KNN(k);
                knn.train("train");
                knn.run("test");
        }
    }

    private void printResults(File file) {
        try {
            Scanner sc = new Scanner(file);
            int correct = 0;
            int yesPredicted = 0;
            int noPredicted = 0;
            for (int i = 0; i < N_FOLDS; ++i) {
                for (String s : folds.get(i)) {
                    String[] split = s.split(",");
                    String line = sc.nextLine();
                    if (line.equals(pair.get(keyThis(split)))) {
                        ++correct;
                    }
                    if (line.equals("yes")) {
                        ++yesPredicted;
                    } else {
                        ++noPredicted;
                    }
                }

            }
            System.out.printf("Yes Predicted: %d\n", yesPredicted);
            System.out.printf("No Predicted: %d\n", noPredicted);
            System.out.printf("Correctly Predicted: %.4f%%\n", (double) (correct*100) / 768);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * TODO
     */
    public void writeToFile() {
        for (int i = 0; i < N_FOLDS; ++i) {
            System.out.printf("fold%d\n", i + 1);
            for (String s : folds.get(i)) {
                System.out.println(s);
            }
            System.out.println("");
        }
    }

}
