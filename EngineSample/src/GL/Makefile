#
# MAKEFILE
#
# GL
#

SRCDIR	= .
OBJDIR	= ../../objs

CC		= g++
CFLAG	= -g -Wall 
GL_OPT  = -framework OpenGL -framework GLUT
INC		= -I ./Matrix -I ./Engine


ENGINE_OBJS = \
			RenderingEngine.o \
			AppEngine.o

MATRIX_OBJS	= \
			ParametricSurface.o


all: $(MATRIX_OBJS) $(ENGINE_OBJS)

$(MATRIX_OBJS):
	$(CC) $(CFLAG) $(INC) -o $(OBJDIR)/$@ -c $(SRCDIR)/Matrix/$(@:.o=.cpp)

$(ENGINE_OBJS):
	$(CC) $(CFLAG) $(GL_OPT) $(INC) -o $(OBJDIR)/$@ -c $(SRCDIR)/Engine/$(@:.o=.cpp)

clean:
	-rm $(OBJDIR)/*.o