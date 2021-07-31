#include <iostream>
#include <vector>

using namespace std;



int e31(int argc, char* argv[]) {

	int N, v; cin >> N >> v;

	vector<int> a(N);

	for (int i = 0; i < N; ++i) cin >> a[i];


	bool exists = false;
	for (int i = 0; i < N; ++i) {
		if (a[i] == v) {
			exists = true;
		}
	}

	cout << (exists ? "Yes" : "No" ) << endl;
}
