#ifndef __CLI_H
#define __CLI_H

#include <list>
#include <string>

typedef void (*cmd)();

struct command {
  std::string name;
  cmd *command;
};


struct cli {
  std::string name;
  std::list<command> commands;
};

cmd find(std::string cmdname);
  
#endif
