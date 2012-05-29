#
#
#  MAKE FILE
#
#

OBJC_CC=clang
CC=gcc

OPT=-Wall

ECHO_OBJC=echom
ECHO_C=echoc
FS_M=fs-m
FS_C=fs-c

help:
	@echo "makefile echo-m|echo-c|fs-m|fs-c"
	@echo " targets"
	@echo "    echo-m  : objective-c echo server"
	@echo "    echo-c  : c language  echo server"
	@echo "    fs-m    : objective-c file server"
	@echo "    fs-c    : c language  file server"
	@echo ""


echo-m:
	$(OBJC_CC) $(OPT) -o $(ECHO_OBJC) echo_server.m -framework Foundation

echo-c:
	$(CC) $(OPT) -o $(ECHO_C) echo_server.c

fs-m:
	$(OBJC_CC) $(OPT) -o $(FS_M) fs_server.m -framework Foundation

fs-c:
	$(CC) $(OPT) -o $(FS_C) fs_server.c

clean:
	@rm $(ECHO_OBJC)
	@rm $(ECHO_C)
	@rm $(FS_M)