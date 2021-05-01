#ifndef __CMD_H_
#define __CMD_H_

#include <string>

typedef int (*op)(int argc, char *argv[]);

typedef struct cmd {
  std::string name;
  op cmd;
} Command;

int add(int a, int b);


#endif // __CMD_H_
