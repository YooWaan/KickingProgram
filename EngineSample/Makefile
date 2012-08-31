#
# MAKEFILE
#
# Http Client Sample
#

SRCDIR	= src
OBJDIR	= objs

TARGET	= gl
CC		= g++
CFLAG	= -g -Wall -framework OpenGL -framework GLUT
INC		= -I ./$(SRCDIR)/GL/Matrix -I ./$(SRCDIR)/GL/Engine
#INC		= -I ./$(SRCDIR)/HttpClient
OBJS	= $(OBJDIR)/*.o
#OBJS	= HttpClient.o \
#		SessionContext.o \
#		BasicMethod.o \
#		XMLContentsDelegate.o

MODULES = \
	GL

#.PHONY=all $(MODULES)
#	$(CC) $(CFLAG) $(INC) -o ${TARGET} $(SRCDIR)/$(TARGET).m $(addprefix $(OBJDIR)/, $(OBJS)) -framework Foundation

all: $(MODULES)
	$(CC) $(CFLAG) $(INC) -o ${TARGET} $(SRCDIR)/main.c $(OBJS) -framework Foundation

module:$(MODULES)

$(MODULES):
	-cd $(addprefix $(SRCDIR)/, $@); make

#$(OBJS):
#	$(CC) $(CFLAG) $(INC) -o $(OBJDIR)/$@ -c $(SRCDIR)/$(@:.o=.m)

clean:
	-rm -rf $(TARGET) $(TARGET).dSYM $(OBJDIR)/*.o
