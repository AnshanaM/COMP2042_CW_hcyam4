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

## Simplification
1. [Simplified] made direct assignment to identifier colideToBreakAndMoveToRight instead of direct assignment
2. [Simplified] used lambda expressions for anonymous methods
3. [UnSimplified] !!!add load and save function to loadsave class!!!
4. []

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
1. fix load save function
2. highscore
3. css design alignment of all the buttons
4. main game title in main menu
5. make all the visible parts to separate scenes
6. fix nullpointerexception error
7. fix concurrentexception error