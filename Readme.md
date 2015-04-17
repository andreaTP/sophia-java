Sophia Java/Scala Bindings
===

**this is still a work in progress**

If you are looking at this page you may be interested in checking out Sophia Db in a JVM environment, the provided interface is still pretty rough for much of the functionalities, but, please, do not be scared!
As soon as you find something that is hard to do just ping me with an issue.

At the moment I have no real world use cases of the db so, please, help me also underlying useful functions to be bumped up to the power of Scala from a scaring plain C form!

## Hot to install

First of all clone sophia db repo

	git clone https://github.com/pmwkaa/sophia.git
than make the db library itself

	cd sophia
	make

clone the java bindings

	cd ..
	git clone https://github.com/andreaTP/sophia-java.git

link the library in the base directory

	cd sophia-java
	ln -s ../sophia/libsophia.so libsophia.so
	#and on osX you may need also
	ln -s ../sophia/libsophia.so.1.2.2 libsophia.so.1.2.2

go into the C bindings dir

	cd LibSophiaJava
	make

##Use it

SBT is the used build tool, please check to start it with the C libraries in the "java.library.path" or use (sbt.sh).

	./sbt.sh
	publishLocal

At this point you can easily add the library at your projects, for example in another sbt project add in build.sbt

	libraryDependencies ++= Seq("unicredit" %% "sophia_java" % "0.0.2-SNAPSHOT")

Please check that the C dependencies are always in your java.library.path during execution of your code.

##Contribute

Test on other systems, pull requests, help requests and improvement proposal of any kind will always be appreciated!