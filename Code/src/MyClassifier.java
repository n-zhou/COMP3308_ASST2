import java.util.Scanner;
import java.io.File;

public class MyClassifier {


  /**
   * Constructor marked private to prevent instantiation outside of this class.
   */
  private MyClassifier() {

  }

  /**
   * The main function reads in the arguments of from the command line.
   * @param args There are 3 arguments expected. args[0] is the training file, args[1] is the test file args[2] is the algorithm to be run.
   *
   */
  public static void main(String[] args) {
    if(args.length != 3)
      return;
    switch(args[2]) {
      case "NB":
      //naive bayes algo
        break;
      default:
        //k nearest neighbour algo
        int k = args[2].charAt(0);
    }
  }

}
