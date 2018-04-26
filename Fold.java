import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * XXX
 * This code is pretty dodgy.
 */
public class Fold {



    /**
     * XXX
     * @return [description]
     */
    @SuppressWarnings("unchecked")
    public static LinkedList[] read() {
        LinkedList<String>[] folds = new LinkedList[10];
        for(int i = 0; i < folds.length; ++i) {
            folds[i] = new LinkedList<>();
        }
        try {
            File file = new File("pima.csv");
            Scanner sc = new Scanner(file);
            int k = 0;
            while (sc.hasNextLine()) {
                folds[(k++) % 10].add(sc.nextLine());
            }

        } catch (IOException e) {
            //do nothing?
        }
        return folds;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            LinkedList<String>[] lines = read();
            File file = new File("pima-folds.csv");

            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < 10; ++i) {
                pw.printf("fold%d\n", i + 1);
                for (String s : lines[i]) {
                    pw.println(s);
                }
                if (i < 9)
                    pw.println("");
                pw.flush();
            }
            pw.close();

        } catch (IOException e) {
            //do nothing lmao
        }
    }

}
