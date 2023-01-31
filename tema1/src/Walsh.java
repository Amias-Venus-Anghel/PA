import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Walsh {
    public static void main(String[] args) {
        try {
            File fin = new File("walsh.in");
            File fout = new File("walsh.out");
            fout.createNewFile();
            Scanner sc = new Scanner(fin);
            FileWriter wr = new FileWriter("walsh.out");
            /* reading input */
            int n, k, x, y;
            n = sc.nextInt();
            k = sc.nextInt();
            for (int i = 0; i < k; i++) {
                x = sc.nextInt();
                y = sc.nextInt();
                /* for each pair (x,y) calculate and write the result */
                if (Walsh.find_value(x - 1, y - 1, n)) {
                    wr.write("1\n");
                } else {
                    wr.write("0\n");
                }
            }
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* returns true for "1" and false for "0" */
    public static boolean find_value(int x, int y, int n) {
        if (n == 1) {
            return false;
        }

        /* finding current cadran
         I  | II
        ----|----
        III | IV
        and modifying the coordonates acordanly */

        if (x < n / 2) {
            /* Cadran I */
            if (y < n / 2) {
                return find_value(x, y, n / 2);
            }
            /* Cadran II */
            return find_value(x, y - n / 2, n / 2);
        }
        /* Cadran III */
        if (y < n / 2) {
            return find_value(x - n / 2, y, n / 2);
        }
        /* Cadran IV */
        return !find_value(x - n / 2, y - n / 2, n / 2);
    }
}
