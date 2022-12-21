all: class main

class:
	javac -cp ./src/: ./src/Codi/Presentacio/Main.java

main: class
	java -cp ./src: Codi/Presentacio/Main
	
clean:
	rm -f ./src/Codi/Presentacio/*.class
	rm -f ./src/Codi/Domini/*.class
	rm -f ./src/Codi/Persistencia/*.class
