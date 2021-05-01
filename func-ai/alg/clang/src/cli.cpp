#include <string>

#include "cli.h"

cmd find(cli *c, std::string name) {
  for (auto &scmd : c->commands) {
    if (scmd.name.compare(name) == 0) {
      return *scmd;
    }
  }
  return nullptr;
}
