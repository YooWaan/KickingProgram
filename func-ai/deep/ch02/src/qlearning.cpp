#include "qlearning.hpp"


int exec_qlearning()
{
 int i;
 int s;
 int t;
 int qvalue[NODENO];

 srand(SEED);

 for(i=0;i<NODENO;++i) {
   qvalue[i]=rand100() ;
 }
 printqvalue(qvalue) ;

 for(i=0;i<QGENMAX;++i){
  s=0;
  for(t=0;t<3;++t){
   s=selecta(s,qvalue) ;
   qvalue[s]=updateq(s,qvalue) ;
  }
  printqvalue(qvalue) ;
 }
 return 0;
}


int updateq(int s,int qvalue[NODENO]) {
 int qv ;/*更新されるQ値*/
 int qmax ;/*Ｑ値の最大値*/
 
 /*最下段の場合*/
 if(s>6){
   if(s==14) {
     qv=qvalue[s]+ALPHA*(1000-qvalue[s]) ;
   }
  /*報酬を与えるノードを増やす*/
  /*他のノードを追加する場合は*/
  /*下記2行のコメントを外す   */
//  else if(s==11)/*報酬の付与*/
//   qv=qvalue[s]+ALPHA*(500-qvalue[s]) ;
   else {
     qv=qvalue[s] ;
   }
 } else { /*最下段以外*/
   if((qvalue[2*s+1])>(qvalue[2*s+2])) {
     qmax=qvalue[2*s+1];
   } else {
     qmax=qvalue[2*s+2];
   }
   qv=qvalue[s]+ALPHA*(GAMMA*qmax-qvalue[s]) ;
 }
 return qv ;
}


int selecta(int olds,int qvalue[NODENO]) {
 int s ;
 /*ε-greedy法による行動選択*/
 if(rand1()<EPSILON){
   /*ランダムに行動*/
   if(rand01()==0) {
     s=2*olds+1 ;
   } else {
     s=2*olds+2 ;
   }
 } else {
   if((qvalue[2*olds+1])>(qvalue[2*olds+2])) {
     s=2*olds+1;
   } else {
     s=2*olds+2;
   }
 }
 return s ;
}


void printqvalue(int qvalue[NODENO]) {
 int i ;
 for (i=1;i<NODENO;++i) {
  printf("%d\t",qvalue[i]);
 }
 printf("\n");
}


double rand1() {
 return (double)rand()/RAND_MAX ;

}

int calc_rand(int num) {
 int rnd ;
 while((rnd=rand())==RAND_MAX) ;
 return (int)((double)rnd/RAND_MAX*num) ;
}

int rand01() {
  return calc_rand(2);
}

int rand100() {
  return calc_rand(101);
}
