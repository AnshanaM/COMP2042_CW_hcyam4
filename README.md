# COMP2042_CW_hcyam4
## Problems
1. [Solved] Issue: UnsupportedThreadException in function Main.stop()
            Solution: changed updateThread.stop() to updateThread.interrupt()
                      changed physicsThread.stop() to physicsThread.interrupt()
                      changed timeThread.stop() to timeThread.interrupt()
2. [Solved] Issue: InterruptedException in all overridden Main.run() functions
            Solution: added return statement in the catch block
                      removed e.printStackTrace()
3. [Solved] Issue: no display of load game button
            Solution: added as child to root of Pane()
4. [Solved] Issue: meaningless label identifier name in Score.show()
            Solution: changed identifier name to scoreIncrement
5. [Solved] Issue: meaningless label identifier name in Score.showMessage()
            Solution: changed identifier name to message
6. [Solved] Issue: meaningless parameter identifier name in Score.showMessage()
            Solution: changed identifier name to msg
7. [Solved] Issue: NullPointerException in LoadSave.saveGame()
            Solution: added assert statement to prevent null pointer from outputStream.flush()
8. [Solved] Issue: save directory and save path were in local disk C
            Solution: changed the addressing relative to the program directory
9. [Solved] Issue: "You Win" is printed twice when no games are saved
            Solution: return statement added to exit the loadGame function
10. [Solved] Issue: in GameEngine class, removed the Initialize method which only implemented one function call
            Solution: directly called the function onAction.OnInit()
11. [Solved]
[NotSolved] Issue: meaningless label identifier name in Score.showGameOver()
            Solution: changed identifier name to gameOverLabel
[NotSolved] Issue: meaningless label identifier name in Score.showWin()
                   Solution: changed identifier name to winLabel
[NotSolved] Issue: IOException when no games saved but load is clicked
                   Solution: {MAKE LABEL FOR THIS TO NOTIFY GAMER}
[NotSolved] Issue: paddle and ball appear when no games saved but load is clicked
                   Solution: {make paddle and ball visibility to false}
[NotSolved] Issue: current game state not saved
                   Solution:

## Refactoring
Because score needs to be protected from unnecessary changes,
I made score a private attribute of the Score class with getter, setter and modifier functions.
Put saveGame and loadGame functions inside LoadSave class and called them from Main.
Also removed the read function fromthe LoadSave class thus reducing number of lines
made the {LIST THE DIFFERENT VARIABLLES} protected static so that it can be accessed directly
from other classes and can be manipulated without using temporary variables like in LoadSave class
renamed choco to bonus, because of changing the graphics to bricks, choco is not meaningful


## Simplification
1. [Simplified] made direct assignment to identifier colideToBreakAndMoveToRight instead of direct assignment
2. [Simplified] used lambda expressions for anonymous methods
3. [UnSimplified] !!!add load and save function to loadsave class!!!
4. [Simplified] by using different coloured brick images, unnecessary lines of code was removed from the Block class
5. 

CHECK IF INITBALL INITBREAK AND INITBBOARD ARE IN ONINIT FUNCTION

## Features
[Implemented] added tutorial button to view instructions on how to play the game
[Implemented] display background
[Implemented] altered ball manipulations to hit at the surface not at the center
[Implemented] game icon graphic added to the window
[Implemented] window size alteration is disabled
[Implemented] when isGoldStauts is true, gold background appears 
[NotImplementedYet] !!!ball initially at paddle center, press space bar to activate ball!!!
[NotImplementedYet] !!!!when ball falls at bottom of screen, it returns to paddle center and space bar will activate it!!!! 
[NotImplementedYet] !!!!!highscore!!!!
[Extended] main menu, and nextlevel or back to menu option is displayed after a level is complete (gameover or player wins)

## Next steps
1. fix load save function [DONE]
2. highscore
3. css design alignment of all the buttons
4. main game title in main menu
5. make all the visible parts to separate scenes
6. fix nullpointerexception error
7. fix concurrentexception error
8. 3 levels of brick breakability
9. multiple balls? first make ball separate class
10. make paddle a separate class
11. paddle wrap around screen penalty