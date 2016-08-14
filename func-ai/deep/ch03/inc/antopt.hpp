#ifndef ANTOPT_HPP
#define ANTOPT_HPP

#include <stdio.h>
#include <stdlib.h>
#include <cmath>

#define NOA 10 /*蟻の個体数*/
#define ILIMIT 50 /*繰り返しの回数*/
#define Q 3 /*フェロモン更新の定数*/
#define RHO 0.8 /*蒸発の定数*/
#define STEP 10 /*道のりのステップ数*/
#define EPSILON 0.15 /*行動選択のランダム性を決定*/
#define SEED 32768 /*乱数のシード*/



int ant_colony_optimize();

#endif
