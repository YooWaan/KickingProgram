
#include <stdio.h>
#include "Engine.hpp"
#include <GLUT/glut.h>

int main(int argc, char* argv[]) {

  printf("Create Engine\n");

  IApplicationEngine* engine = CreateAppEngine();
  //IRenderingEngine* engine = CreateEngine();
  engine->Initialize(argc, argv);

  glutMainLoop();
  printf("Hello\n");
  return 0;
}

