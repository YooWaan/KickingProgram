#include "antopt.hpp"


int rand01() {
  int rnd ;
  while((rnd=rand())==RAND_MAX) ;
  return (int)((double)rnd/RAND_MAX*2) ;
}

double rand1() {
  return (double)rand()/RAND_MAX ;
}

void printp(double pheromone[2][STEP]) {
  int i,j ;

  for(i=0;i<2;++i){
    for(j=0;j<STEP;++j) {
      printf("%4.2lf ",pheromone[i][j]) ;
    }
    printf("\n") ;
  }
}

void printmstep(int mstep[NOA][STEP]) {
  int i,j ;
  printf("*mstep\n") ;
  for (i=0;i<NOA;++i) {
    for (j=0;j<STEP;++j) {
      printf("%d ",mstep[i][j]) ;
    }
    printf("\n") ;
  }
}

void update(int cost[2][STEP],double pheromone[2][STEP],int mstep[NOA][STEP] ) {
  int m ;/*蟻の個体番号*/
  int lm ;/*蟻の歩いた距離*/
  int i,j ;
  double sum_lm=0 ;/*蟻の歩いた合計距離*/
 
  /*フェロモンの蒸発*/
  for(i=0;i<2;++i)
    for(j=0;j<STEP;++j)
      pheromone[i][j]*=RHO ;

  /*蟻による上塗り*/
  for (m=0;m<NOA;++m) {
    /*個体mの移動距離lmの計算*/
    lm=0 ;
    for(i=0;i<STEP;++i) {
      lm+=cost[mstep[m][i]][i] ;
    }
    /*フェロモンの上塗り*/
    for(i=0;i<STEP;++i) {
      pheromone[mstep[m][i]][i]+=Q*(1.0/lm) ;
    }
    sum_lm+=lm ;
  }

  /*蟻の歩いた平均距離を出力*/
  printf("%lf\n",sum_lm/NOA) ;
}


void walk(int cost[2][STEP], double pheromone[2][STEP],int mstep[NOA][STEP]){
  int m;
  int s;
  for(m=0;m<NOA;++m){
    for(s=0;s<STEP;++s){
      /*ε-greedy法による行動選択*/
      if((rand1()<EPSILON)
         ||(std::abs(pheromone[0][s]-pheromone[1][s])<1e-9)) {/*ランダムに行動*/
        mstep[m][s]=rand01() ;
      } else {
        /*フェロモン濃度により選択*/
        if(pheromone[0][s]>pheromone[1][s]) {
          mstep[m][s]=0 ;
        } else {
          mstep[m][s]=1 ;
        }
      }
    }
  }
  /*蟻の挙動の出力*/
  printmstep(mstep) ;
}

int ant_colony_optimize() {
  int cost[2][STEP]={/*各ステップのコスト(距離）*/
    {1,1,1,1,1,1,1,1,1,1},
    {5,5,5,5,5,5,5,5,5,5}};
  double pheromone[2][STEP]={0} ;/*各ステップのフェロモン量*/
  int mstep[NOA][STEP] ;/*蟻が歩いた過程*/
  int i;/*繰り返し回数の制御*/

  /*乱数の初期化*/
  srand(SEED) ;

  /*最適化の本体*/
  for(i=0;i<ILIMIT;++i){
    /*フェロモンの状態出力*/
    printf("%d:\n",i) ;
    printp(pheromone) ;
    /*蟻を歩かせる*/
    walk(cost,pheromone,mstep) ;
    /*フェロモンの更新*/
    update(cost,pheromone,mstep) ;
  }
  /*フェロモンの状態出力*/
  printf("%d:\n",i) ;
  printp(pheromone) ;

  return 0 ;
}

  
