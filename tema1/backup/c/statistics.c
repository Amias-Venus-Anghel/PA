#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>
#include <stdbool.h>

#define MAX  1<<15


int maxAppearanceCount(char* word) {
    if (!word)
        return 0;

    int max_count = 0;
    int counter[256] = {0};
    for (int i = 0; i < strlen(word); i++) {
        counter[word[i]] += 1;
        if (counter[word[i]] > max_count)
            max_count = counter[word[i]];
    }

    return max_count;
}

int compare(const void* a, const void* b) {
    char* word_1 = (char*)a;
    char* word_2 = (char*)b;

    int app1 = maxAppearanceCount(word_1);
    int app2 = maxAppearanceCount(word_2);

    if (app1 == app2 )
        return 0;
    else
        if ( app1 < app2 )
            return -1;
        else
            return 1;
}
bool hasDomLetter(char* word) {
    return maxAppearanceCount(word) > strlen(word)/2;
}

int maxWords(char ** words, int n) {
    qsort(words, n, sizeof(char*), compare);

    char* buffer_sol = (char*) malloc(100000);
    int counter = 0;
    if (!buffer_sol)
        return 0;

    for (int i = 0; i < n; i++) {
        char* buffer = (char*) malloc(100000);
        if (!buffer) {
            free(buffer_sol);
            return 0;
        }

        strcat(buffer, buffer_sol);
        strcat(buffer, words[i]);

        if (hasDomLetter(buffer)) {
            counter++;
            strcat(buffer_sol, words[i]);
        }
    }

    free(buffer_sol);
    return counter;
}

void freeWords(char** words, int n) {
    for (int i = 0; i < n ; i++)
        free(words[i]);
    free(words);
}

int main() {
    FILE *fin = fopen("statistics.in", "r");
    FILE *fout = fopen("statistics.out", "w");

    int n;
    fscanf(fin, "%d", &n);
    printf("AICI n = %d\n", n);

    char** words = (char**) malloc(n);
    if (!words)
        return 1;

    for (int i = 0; i < n; i++) {
        words[i] = (char*) malloc(MAX);
        if (!words[i]) {
            // freeWords(words, i);
            return 1;
        }
        fscanf(fin, "%s", words[i]);
    }
    fprintf(fout, "%d", maxWords(words, n));

    // freeWords(words, n);
    fclose(fin);
    fclose(fout);

    return 0;
}
