import java.util.Scanner;
import java.io.File;

public class MyClassifier {

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
