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
 int qv ;/*���������Q��*/
 int qmax ;/*���ͤκ�����*/
 
 /*�ǲ��ʤξ��*/
 if(s>6){
   if(s==14) {
     qv=qvalue[s]+ALPHA*(1000-qvalue[s]) ;
   }
  /*�󽷤�Ϳ����Ρ��ɤ����䤹*/
  /*¾�ΥΡ��ɤ��ɲä������*/
  /*����2�ԤΥ����Ȥ򳰤�   */
//  else if(s==11)/*�󽷤���Ϳ*/
//   qv=qvalue[s]+ALPHA*(500-qvalue[s]) ;
   else {
     qv=qvalue[s] ;
   }
 } else { /*�ǲ��ʰʳ�*/
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
 /*��-greedyˡ�ˤ���ư����*/
 if(rand1()<EPSILON){
   /*������˹�ư*/
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
