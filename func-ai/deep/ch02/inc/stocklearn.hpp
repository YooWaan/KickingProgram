#ifndef STOCKLEARN_HPP
#define STOCKLEARN_HPP

#define SETSIZE 100
#define CNO 10
#define GENMAX 10000
#define SEED 32767


void readdata(int data[SETSIZE][CNO], int teacher[SETSIZE]);
int rand012();
int calcscore(int data[SETSIZE][CNO], int teacher[SETSIZE], int answer[CNO]);
int exec_learn();

#endif
