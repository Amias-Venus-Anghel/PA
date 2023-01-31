CC = gcc

build: walsh.o statistics.o

run-p1: walsh.o
		./walsh.o

run-p2: statistics.o
		./statistics.o

walsh.o: walsh.c
		$(CC) $^ -o $@

statistics.o: statistics.c
		$(CC) $^ -o $@

.PHONY: clean

clean:
		rm -f *.o *.out