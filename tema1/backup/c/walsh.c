#include <stdio.h>
#include <stdarg.h>
#include <stdbool.h>

#define N_MAX = 1 << 30;

bool find_value(int x, int y, int n) {
    if (n == 1)
        return false;

    /* finding current cadran
         I  | II
        ----|----
        III | IV
        and modifying the coordonates acordanly */

    if (x < n / 2) {
        /* Cadran I */
        if (y < n / 2)
            return find_value(x, y, n / 2);
        /* Cadran II */
        return find_value(x, y - n / 2, n / 2);
    }
    /* Cadran III */
    if (y < n / 2)
        return find_value(x - n / 2, y, n / 2);
    /* Cadran IV */
    return !find_value(x - n / 2, y - n / 2, n / 2);
}

int main() {
    FILE *fin = fopen("walsh.in", "r");
    FILE *fout = fopen("walsh.out", "w");

    int n, k;
    fscanf(fin, "%d%d", &n, &k);
    for (int i = 0; i < k; i++) {
        int x, y;
        fscanf(fin, "%d%d", &x, &y);
        if (find_value(x - 1, y - 1, n))
            fprintf(fout, "%d\n", 1);
        else
            fprintf(fout, "%d\n", 0);
    }

    fclose(fin);
    fclose(fout);

    return 0;
}
