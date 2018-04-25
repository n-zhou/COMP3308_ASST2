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
    * The main function reads in the arguments of from the command line.
    * @param args There are 3 arguments expected. args[0] is the training file, args[1] is the test file args[2] is the algorithm to be run.
    *
    */
    public static void main(String[] args) {
        if(args.length != 3)
            return;
        switch (args[2]) {
            case "NB":
                NaiveBayes(args[0], args[1]);
                break;
            default:
                //k nearest neighbour algo
                int k = args[2].charAt(0);
        }
    }

}
