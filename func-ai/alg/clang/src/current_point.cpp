#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

// chapter 2.4

double clac_dist(double x1, double y1, double x2, double y2) {
  return sqrt( (x1-x2) * (x1 - x2) - (y1-y2) * (y1-y2) );
}


int current_point(int argc, char* argv[]) {

  int N; cin >> N;

  vector<double> x(N), y(N);

  cout << "N=" << N << endl;

  for (int i = 0; i < N; ++i) cin >> x[i] >> y[i];

  double min_dist = 10000000.0;

  for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {

      double dist_i_j = clac_dist(x[i], y[i], y[j], y[j]);

      if (dist_i_j < min_dist) {
        min_dist = dist_i_j;
      }
    }
  }

  cout << min_dist << endl;

  return 0;
}
