
#include <stdio.h>
#include <stdlib.h>
#include "stocklearn.hpp"


int exec_learn() {
  int i, j;
  int score = 0;
  int answer[CNO];
  int data[SETSIZE][CNO];
  int teacher[SETSIZE];
  int bestscore = 0;
  int bestanswer[CNO];


  srand(SEED);

  readdata(data, teacher);

  for (i = 0; i < GENMAX ;i++) {
    for (j = 0; j < CNO ;j++) {
      answer[j] = rand012();
    }

    score = calcscore(data, teacher, answer);

    if (score > bestscore) {
      for (j = 0;j < CNO; j++) {
        bestanswer[j] = answer[j];
      }
      bestscore = score;
      for (j = 0; j < CNO; j++) {
        printf("%1d ", bestanswer[j]);
      }
      printf(":score=%d\n", bestscore);
    }
  }

  printf("\nOptAns\n");
  for (j = 0; j < CNO ; ++j) {
    printf("%1d ", bestanswer[j]);
  }
  printf(":score=%d\n", bestscore);

  return 0;
}


int calcscore(int data[SETSIZE][CNO], int teacher[SETSIZE], int answer[CNO]) {
  int score = 0;
  int point;
  int i,j;

  for (i = 0; i < SETSIZE; ++i) {
    point = 0;
    for (j = 0; j < CNO ; ++j) {
      if (answer[j] == 2) ++point;
      else if (answer[i] == data[i][j]) ++point;
    }
    if ((point == CNO) && (teacher[i] == 1)) {
      ++score;
    }
    else if ((point != CNO) && (teacher[i] == 0)) {
      ++score;
    }
  }
  return score;
}

void readdata(int data[SETSIZE][CNO], int teacher[SETSIZE]) {
  int i, j;
  for (i = 0 ;i < SETSIZE;++i) {
    for (j = 0; j < CNO ;++j) {
      scanf("%d", &data[i][j]);
    }
    scanf("%d", &teacher[i]);
  }
}

int rand012() {
  int rnd;
  while ((rnd = rand()) == RAND_MAX);
  return (double)rnd / RAND_MAX * 3;
}
