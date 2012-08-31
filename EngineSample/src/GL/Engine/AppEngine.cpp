//
// AppEngine.cpp -
//
//
//
// Created by wooyoowaan@gmail.com on Thu Aug 30 17:06:45 2012
// Copyright 2012 by yoowaan. All rights reserved.
//

#include "Engine.hpp"
#include "ParametricEquations.hpp"
#include <GLUT/glut.h>
#include <iostream>

#define SURFACE_COUNT 1

class AppEngine : public IApplicationEngine {
public:
  AppEngine(IRenderingEngine* renderingEngine);
  ~AppEngine();
  void Initialize(int argc, char* argv[]);
  static void Render();
private:
  void PopulateVisuals(Visual* visuals) const;
  bool spinning;
  IRenderingEngine* renderer;
  Quaternion orientation;  
};

static AppEngine* appEngine = NULL;

IApplicationEngine* CreateAppEngine() {
  std::cout << appEngine << std::endl;
  if (appEngine == NULL) {
	appEngine = new AppEngine(CreateEngine());
  }
  return appEngine;
}

AppEngine::AppEngine(IRenderingEngine* renderingEngine) :
  spinning(false), 
  renderer(renderingEngine) {
  std::cout << "Construct----" << std::endl;
}

AppEngine::~AppEngine() {
  delete renderer;
}

void AppEngine::Initialize(int argc, char* argv[]) {
  std::cout << "Initialize" << std::endl;

  glutInit(&argc, argv);
  glutCreateWindow(argv[0]);
  glutDisplayFunc(AppEngine::Render);

  vector<ISurface*> surfaces(SURFACE_COUNT);
  surfaces[0] = new Sphere(1.4f);
  renderer->Initialize(surfaces);
  for (int i = 0; i < SURFACE_COUNT ; i++) {
	delete surfaces[i];
  }

  std::cout << "Initialize Done" << std::endl;
}

void AppEngine::Render() {
  vector<Visual> visuals(SURFACE_COUNT);
  appEngine->PopulateVisuals(&visuals[0]);
  appEngine->renderer->Render(visuals);
}



void AppEngine::PopulateVisuals(Visual* visuals) const {
  visuals[ 0 ].Color = spinning ? vec3(1, 1, 1) : vec3(0, 1, 1);
  visuals[ 0 ].LowerLeft = ivec2(0, 48);
  visuals[ 0 ].ViewportSize = ivec2(320, 432);
  visuals[ 0 ].Orientation = orientation;
}
