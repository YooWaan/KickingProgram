//
// RenderingEngine.cpp -
//
//
//
// Created by wooyoowaan@gmail.com on Thu Aug 30 16:03:59 2012
// Copyright 2012 by yoowaan. All rights reserved.
//

#include "Engine.hpp"

#include <iostream>
#include <GLUT/glut.h>

struct Drawable {
    GLuint VertexBuffer;
    GLuint IndexBuffer;
    int IndexCount;
};

class RenderingEngine : public IRenderingEngine {
public:
  RenderingEngine();
  void Initialize(const vector<ISurface*>& surfaces);
  void Render(const vector<Visual>& visuals) const;
private:
  vector<Drawable> m_drawables;
  GLuint m_colorRenderbuffer;
  mat4 m_translation;
};

IRenderingEngine* CreateEngine() {
  return new RenderingEngine();
}

RenderingEngine::RenderingEngine() {
}

void RenderingEngine::Initialize(const vector<ISurface*>& surfaces) {

  glGenRenderbuffers(1, &m_colorRenderbuffer);
  std::cout << "Bind Buffer----" << std::endl;
  glBindRenderbuffer(GL_RENDERBUFFER, m_colorRenderbuffer);


  vector<ISurface*>::const_iterator surface;
  for (surface = surfaces.begin(); surface != surfaces.end(); ++surface) {

	// 頂点用のVBOを作成する
	vector<float> vertices;
	(*surface)->GenerateVertices(vertices);
	GLuint vertexBuffer;
	glGenBuffers(1, &vertexBuffer);
	glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
	glBufferData(GL_ARRAY_BUFFER,
				 vertices.size() * sizeof(vertices[0]),
				 &vertices[0],
				 GL_STATIC_DRAW);

	// 必要に応じ、インデックス用のVBOを新たに作成する
	int indexCount = (*surface)->GetLineIndexCount();
	GLuint indexBuffer;
	if (!m_drawables.empty() && indexCount == m_drawables[0].IndexCount) {
	  indexBuffer = m_drawables[0].IndexBuffer;
	} else {
	  vector<GLushort> indices(indexCount);
	  (*surface)->GenerateLineIndices(indices);
	  glGenBuffers(1, &indexBuffer);
	  glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
	  glBufferData(GL_ELEMENT_ARRAY_BUFFER,
				   indexCount * sizeof(GLushort),
				   &indices[0],
				   GL_STATIC_DRAW);
	}

	Drawable drawable = { vertexBuffer, indexBuffer, indexCount};
	m_drawables.push_back(drawable);
  }

  // フレームバッファオブジェクトを作成する
  GLuint framebuffer;
  glGenFramebuffers(1, &framebuffer);
  glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
  glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0,
							GL_RENDERBUFFER, m_colorRenderbuffer);
  glBindRenderbuffer(GL_RENDERBUFFER, m_colorRenderbuffer);

  glEnableClientState(GL_VERTEX_ARRAY);
  m_translation = mat4::Translate(0, 0, -7);
}

void RenderingEngine::Render(const vector<Visual>& visuals) const {
  glClearColor(0.5f, 0.5f, 0.5f, 1);
  glClear(GL_COLOR_BUFFER_BIT);
    
  vector<Visual>::const_iterator visual = visuals.begin();
  for (int visualIndex = 0; visual != visuals.end(); ++visual, ++visualIndex) {
        
	// ビューポート変換行列を設定する
	ivec2 size = visual->ViewportSize;
	ivec2 lowerLeft = visual->LowerLeft;
	glViewport(lowerLeft.x, lowerLeft.y, size.x, size.y);

	// モデルビュー変換行列を設定する
	mat4 rotation = visual->Orientation.ToMatrix();
	mat4 modelview = rotation * m_translation;
	glMatrixMode(GL_MODELVIEW);
	glLoadMatrixf(modelview.Pointer());
        
	// 投影変換行列を設定する
	float h = 4.0f * size.y / size.x;
	mat4 projection = mat4::Frustum(-2, 2, -h / 2, h / 2, 5, 10);
	glMatrixMode(GL_PROJECTION);
	glLoadMatrixf(projection.Pointer());
        
	// 色を設定する
	vec3 color = visual->Color;
	glColor4f(color.x, color.y, color.z, 1);
        
	// ワイヤーフレームを描画する
	int stride = sizeof(vec3);
	const Drawable& drawable = m_drawables[visualIndex];
	glBindBuffer(GL_ARRAY_BUFFER, drawable.VertexBuffer);
	glVertexPointer(3, GL_FLOAT, stride, 0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, drawable.IndexBuffer);
	glDrawElements(GL_LINES, drawable.IndexCount, GL_UNSIGNED_SHORT, 0);
  }
}
