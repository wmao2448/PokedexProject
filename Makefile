default: runProgram

#Main Program
PokedexDriver.class: PokedexDriver.java
	javac PokedexDriver.java

Pokedex.class: Pokedex.java
	javac Pokedex.java

Pokemon.class: Pokemon.java
	javac Pokemon.java

MapADT.class: MapADT.java
	javac MapADT.java

HashTableMap.class: HashTableMap.java
	javac HashTableMap.java

runProgram: PokedexDriver.class Pokedex.class Pokemon.class MapADT.class HashTableMap.class
	java PokedexDriver


#JUnit Tests
PokedexTester.class: PokedexTester.java PokedexDriver.java
	javac -cp .:junit5.jar PokedexTester.java -Xlint

test: PokedexTester.class 
	java -jar junit5.jar --class-path . --scan-classpath -n PokedexTester

#Clean
clean:
	rm *.class
