CC=javac
DIR=gol

all: GameOfLife GameOfLife.jar run

GameOfLife:
	$(CC) $(DIR)/*.java


GameOfLife.jar:
	jar cfmv $@ Manifest $(DIR)/*.class


run: GameOfLife.jar
	java  -jar GameOfLife.jar

clean: 
	$(RM) $(DIR)/*~  $(DIR)/*.class GameOfLife.jar
