#include <iostream>
#include <string>
using namespace std;

#include "cli.h"
#include "ex1.h"

int main() {

  command oncmd = {string(), on};
  array<command> cs = {oncmd};
  cli c = {string("cmd"),cs};

  string cmd_name = "on";
  
  auto run = find(&c, cmd_name);

  cout << run << endl;

  return 0;
}
