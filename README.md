# Human Benchmark
## CS 351 Project 3

Ashley Krattiger

Main for this program is in HumanBenchmarkDisplay. This 
program takes no arguments from command line. To play the 
games, simply press the "Play" button located in the same
square as the name of the game you want to play. Each game
runs entirely in its own window separate from the main menu.
You will not be able to interact with the main menu while the
game windows are open. When a game starts, it will open 
with the game window and another pop up window which 
explains the instructions for the game. When the game is 
over, another window will pop up declaring your score and if 
you got a high score, and will provide buttons 
with options to either replay the game or go back to the
main menu. The main menu will keep the records of high scores
up to date while you are playing the games. High scores do
not carry over after you close the main menu. To exit the 
program, just close the window.



### Chimp Test Game

To play this game, click on the squares where the numbers
appear in numerical order. The game will move between 
rounds automatically when you complete one. The game will
begin each round by showing the numbers in their squares
for about 3 seconds. Score increases with every successful
round completed. Game ends when you click the wrong box 3
times (get 3 strikes). Strikes carry over between rounds.

Known issues: I was having a bug where sometimes one of the
numbers would not show up on the screen. I believe this 
issue was caused by the random number generator duplicating
positions. If so, this should be fixed and you should not 
see this bug. I left a line of commented 
debugging code in the function playGame() which prints out
the locations of all the numbers.