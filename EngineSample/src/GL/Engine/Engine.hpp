#ifndef ENGINE_HPP
#define ENGINE_HPP

#include "Vector.hpp"
#include "Quaternion.hpp"
#include <vector>

using std::vector;

struct IApplicationEngine {
  virtual void Initialize(int argc, char* argv[]) = 0;
  static void Render();
  /*
    virtual void UpdateAnimation(float timeStep) = 0;
    virtual void OnFingerUp(ivec2 location) = 0;
    virtual void OnFingerDown(ivec2 location) = 0;
    virtual void OnFingerMove(ivec2 oldLocation, ivec2 newLocation) = 0;
  */
    virtual ~IApplicationEngine() {}
};

struct ISurface {
    virtual int GetVertexCount() const = 0;
    virtual int GetLineIndexCount() const = 0;
    virtual void GenerateVertices(vector<float>& vertices) const = 0;
    virtual void GenerateLineIndices(vector<unsigned short>& indices) const = 0;
    virtual ~ISurface() {}
};

struct Visual {
    vec3 Color;
    ivec2 LowerLeft;
    ivec2 ViewportSize;
    Quaternion Orientation;
};

struct IRenderingEngine {
  virtual void Initialize(const vector<ISurface*>& surfaces) = 0;
  virtual void Render(const vector<Visual>& visuals) const = 0;
  virtual ~IRenderingEngine() {}
};

IApplicationEngine* CreateAppEngine();
IRenderingEngine* CreateEngine();

#endif
