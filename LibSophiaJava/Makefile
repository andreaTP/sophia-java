MAKEFLAGS = --no-print-directory

TARGETS = \

default: all

clean: FORCE
	rm *.o
	rm -f ../libSophiaInterface.so

all:
	gcc -Wall -O3 -fpic -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -c SophiaInterface.c -o SophiaInterface.o
	g++ -shared -L$JAVA_HOME/include -L$JAVA_HOME/include/linux SophiaInterface.o  -o ../libSophiaJava.so  ./libsophia.so

FORCE:
