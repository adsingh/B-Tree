all: prog1

prog1: treesearch.java
	javac treesearch.java

clean:
	rm *.class
