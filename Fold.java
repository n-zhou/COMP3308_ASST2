import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Comparator;

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
        LinkedList<String> input = new LinkedList<>();
        LinkedList<String>[] folds = new LinkedList[10];
        for(int i = 0; i < folds.length; ++i) {
            folds[i] = new LinkedList<>();
        }
        try {
            File file = new File("pima.csv");
            Scanner sc = new Scanner(file);
            int k = 0;
            while (sc.hasNextLine()) {
                input.add(sc.nextLine());
            }
            input.sort(new Comparator<String>() {

                public int compare(String s1, String s2) {
                    return s1.split(",")[8].compareTo(s2.split(",")[8]);
                }

            });
            while (!input.isEmpty()) {
                folds[(k++) % 10].add(input.poll());
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
