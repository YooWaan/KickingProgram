#include <iostream>
using namespace std;


void on() {
  int N;
  cin >> N ;


  int count =0;
  for (int i = 0; i < N ; ++i) {
    ++count;
  }

  cout << count << endl;
}
