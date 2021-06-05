#include <iostream>
#include <list>
#include <algorithm>

#include "cmd.h"
#include "ex1.h"

using namespace std;

int help(int argc, char *argv[]) {
  cout << "help" << endl;
  return 0;
}


int main(int argc, char *argv[]) {

  list<Command> commands;
  string name = "help";

  if (argc > 1) {
    name.assign(argv[1]);
  }

  Command cmd = Command{ "help", help },
    cp = Command{"cp", current_point};

  commands.push_back(cmd);
  commands.push_back(cp);


  auto result = find_if(commands.cbegin(), commands.cend(), [name](Command c) {
      return c.name.compare(name) == 0;
    });
  if (result == commands.cend()) {
    return 0;
  }
  return result->cmd(argc, argv);
}