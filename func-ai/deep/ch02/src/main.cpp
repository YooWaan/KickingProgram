#include "stocklearn.hpp"
#include "qlearning.hpp"


int main(int argc, char * argv[]) {
  if (argc >= 1) {
    return exec_qlearning();
  }
  return exec_learn();
}
