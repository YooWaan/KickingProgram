#ifndef QLEARNING_HPP
#define QLEARNING_HPP

#include <stdio.h>
#include <stdlib.h>

#define GENMAX 1000
#define NODENO  15
#define ALPHA   0.1
#define GAMMA   0.9
#define EPSILON 0.3
#define SEED    32767

int rand100();
int rand01();
double rand1();
void printqvalue(int qvalue[NODENO]);
int selecta(int s, int qvalue[NODENO]);
int updateq(int s, int qvalue[NODENO]);

int exec_qlearniing();

#endif
