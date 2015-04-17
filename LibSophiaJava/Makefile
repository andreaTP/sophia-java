MAKEFLAGS = --no-print-directory

OS=$(shell uname)

ifeq (${OS},Darwin)
	LIBEXTENSION=jnilib
	LINK=-dynamiclib -framework JavaVM -o ../libSophiaJava.${LIBEXTENSION} SophiaInterface.o ./libsophia.so
else 
	LIBEXTENSION=so
	LINK=-shared -L$JAVA_HOME/include -L$JAVA_HOME/include/linux SophiaInterface.o  -o ../libSophiaJava.so  ./libsophia.so
endif

default: all

clean: FORCE
	rm *.o
	rm -f ../libSophiaInterface.*
	rm -f ../libSophiaJava.*

all:
	@cc -Wall -O3 -fpic -c -I${JAVA_HOME}/include SophiaInterface.c -I${JAVA_HOME}/include/${OS} -o SophiaInterface.o
	@cc ${LINK}
FORCE:
