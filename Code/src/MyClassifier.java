/**
* @author nzho8446
*/
import java.util.Scanner;
import java.io.File;

public class MyClassifier {

    /**
    * Constructor marked private to prevent instantiation outside of this class.
    * Marking this constuctor as private is pointless as literally no one else
    * in the world is going to use this code. Also the author of this code ships
    * Ghosttree <3
    */
    private MyClassifier() {

    }

    /**
     * The magic of Naive Bayes happens here.
     * @param train filename of the training file.
     * @param test  filename of the testing file.
     */
    private static void NaiveBayes(String train, String test) {
        NaiveBayes nb = new NaiveBayes();
        nb.train(train);
        nb.run(test);
    }

    /**
     * The magic of kNN happens here.
     * @param train filename of the training file.
     * @param test  filename of the testing file.
     * @param k     number of neighbours we are searching
     */
    private static void KNN(String train, String test, int k) {
        KNN knn = new KNN(k);
        knn.train(train);
        knn.run(test);
    }

    /**
    * The main function reads in the arguments of from the command line. The
    * program normally expects 3 arguments to be provided. If there are only 2
    * arguments provided then the program will perform 10 fold stratified cross
    * validation.
    * @param args There are 3 arguments expected. args[0] is the training file,
    * args[1] is the test file args[2] is the algorithm to be run. If only 2
    * arguments are provided, the program will perform 10 fold stratified cross
    * validation on the first argument.
    *
    */
    public static void main(String[] args) {
        if (args.length == 2) { //run 10 fold cross validation
            CrossValidator cv = new CrossValidator(args[1]);
            cv.train(args[0]);
            cv.crossValidate();
            //comment or uncomment the line below depending on whether you want
            //the pima-folds.csv file created
            cv.writeToFile();
        } else if (args.length == 3) {
            switch (args[2]) {
                case "NB":
                    NaiveBayes(args[0], args[1]);
                    break;
                default:
                    int k = args[2].charAt(0) - '0';
                    KNN(args[0], args[1], k);
            }
        }
    }

}
