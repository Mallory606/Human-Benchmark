# Human Benchmark
## CS 351 Project 3

Ashley Krattiger

I am not done with this project yet and will be using 3
late days.

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



### Reaction Time Game

To play this game, simply click on the screen when it turns 
from red to green. If you click too early, it will restart
the game automatically. The score for this game is in 
milliseconds.



###Aim Trainer Game

To play this game, click the target 30 times as fast as you
can. The target will move to a random location on the 
screen each time it is clicked. The score for this game is
your average reaction time in milliseconds.



### Chimp Test Game

To play this game, click on the squares where the numbers
appear in numerical order. The game will move between 
rounds automatically when you complete one. The game will
begin each round by showing the numbers in their squares
until a square is clicked. Score increases with every 
successful round completed. Game ends when you click the 
wrong box 3 times (get 3 strikes). Strikes carry over 
between rounds. The game will end automatically if you 
complete a round with 30 numbers.

Known issues: I was having a bug where sometimes one of the
numbers would not show up on the screen. I believe this 
issue was caused by the random number generator duplicating
positions. If so, this should be fixed and you should not 
see this bug. I left a line of commented 
debugging code in the function playGame() which prints out
the locations of all the numbers.



### Visual Memory Game

This game begins by flashing several white squares on the
screen for about 1.5 seconds. Once the original visual 
disappears, press on the same squares that were 
highlighted. If you press an incorrect square, it will highlight
in red. You are allowed 3 mistakes in a round before it cuts
you off and you lose one of your 3 lives. Score increases 
with every successful round completed. Game ends when 
you lose 3 lives or complete a round with 20 squares.



### Typing Game

This game will randomly choose a passage from 5 possible
passages and will display it on the screen. Type in the text 
field on the bottom of the screen and it will compare what 
you have typed with the given passage. If something does not
match the given passage, that character on the given passage 
will change to red. If your input is correct, the character 
will change to green. Once your input perfectly matches the 
given passage, the game will end and it will give you your
score of typing speed in words per minute.



### Number Memory Game

To play this game, first memorize the number while it is on
the screen. After 3 seconds it disappears, and the text field
will appear, so you can enter in the number as you 
remember it. Press the "Submit" button to check your 
answer. At the checking screen the "Next" button will also
appear. Press this button to either move on the the next 
round if you are successful or to bring up the Game Over 
pop up if you are not. Non number input automatically ends 
the game. Input with less digits than the given number will 
fill the empty space with zeros on the screen. Input with
more digits than the given number will display all extra
digits in red and will be off center on the screen.



### Verbal Memory Game

To play this game, press one of the two buttons on the
screen for each word that pops up. Press "New" if you
haven't seen the word yet in the session or press "Seen"
if you have seen the word. You have 3 lives, and you lose
one every time you make a mistake. The final score is how
many rounds you made it through. Each time a word is
generated, there is a 1 in 4 chance that it will pick a word 
from the list of words that have already been seen.