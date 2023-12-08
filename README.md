# COMP2042_CW_hcyam4
## Problems
1. [Solved] Issue: UnsupportedThreadException in function Main.stop()
            Solution: changed updateThread.stop() to updateThread.interrupt()
                      changed physicsThread.stop() to physicsThread.interrupt()
                      changed timeThread.stop() to timeThread.interrupt()
2. [Solved] Issue: InterruptedException in all overridden Main.run() functions
            Solution: added return statement in the catch brick
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
11. [Solved] Issue: game label animations would stop at times and freeze on screen
            Solution: 
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
-----
Because score needs to be protected from unnecessary changes,
I made score a private attribute of the Score class with getter, setter and modifier functions.
Put saveGame and loadGame functions inside LoadSave class and called them from Main.
Also removed the read function fromthe LoadSave class thus reducing number of lines
made the {LIST THE DIFFERENT VARIABLES} protected static so that it can be accessed directly
from other classes and can be manipulated without using temporary variables like in LoadSave class
renamed choco to bonus, because of changing the graphics to bricks, choco is not meaningful
-----
made Animation class to add the animations to the different labels and to remove the label
name restart game to gameRestart becuase it can be easily confused with retrying the same level
made the ball start from the paddle and moves upwards not downwards, this makes it fair for the player
changed conditional statements for gorightball and godownball with ternary operator to make the code 
shorter and easier to understand
moved initBoard function to Block class and is static because it does not belong to the brick instance
added heart animation NEED TO CHANGE THE SIZE OF THE HEART AND SPEED
made the collsision detection of ball and brick more smoother
in setPhysicsToBall in Main class, made direct assingments to goRightBall and goDownBall instead of indirectly
assigning to colideToRightBlock, colideToTopBlock, colideToBottomBlock adn colideToLeftBlock
changed move function name to movePaddle because it can be easily confused with moving the ball
removed not used variable called oldXBreak
ball was moving too fast abnormally, so updated it by increasing it by a small fraction,
this issue was caused by the ball relation section of the code where the balls speed would depend on the
the ball used to slow down when hit at the center of the paddle but moved the fastest when hit at the edge
this issue is also solved by the above solution
level, break and ball position
renamed all chocos to meaningful names like bonus or bonusObj
addition of next level button allows player to choose whether they want to play next level or fo back to menu or exit
heart animation
retry button the level remains same but heart becomes 3
back to menu hearts dont change and level becomes 1




## Simplification
1. [Simplified] made direct assignment to identifier colideToBreakAndMoveToRight instead of indirect assignment
2. [Simplified] used lambda expressions for anonymous methods
3. [Simplified] added load and save function to loadsave class
4. [Simplified] by using different coloured brick images, unnecessary lines of code was removed from the Block class
5. 


## Features
[Implemented] added tutorial button to view instructions on how to play the game
[Implemented] display background
[Implemented] added external font for the game labels
[Implemented] altered ball manipulations to hit at the surface not at the center
[Implemented] game icon graphic added to the window bar
[Implemented] window size alteration is disabled
[Implemented] when isGoldStauts is true, gold background appears 
[NotImplementedYet] !!!ball initially at paddle center, press space bar to activate ball!!!
[NotImplementedYet] !!!!when ball falls at bottom of screen, it returns to paddle center and space bar will activate it!!!! 
[NotImplementedYet] !!!!!highscore!!!!
[Extended] main menu, and nextlevel or back to menu option is displayed after a level is complete (gameover or player wins)

## Next steps
1. fix load save function [DONE]
2. for refactoring, identify which methods are not used in the instance but is called in a class's method can make
    it a static method
2. highscore
3. fix the menu which does not show [DONE]
4. DO PLAY HEART ANIMATION [DONE]
4. fix the load game, nothing shows up
3. css design alignment of all the buttons
4. main game title in main menu [DONE]
6. fix nullpointerexception error
7. fix concurrentexception error
8. 3 levels of brick breakability: make the last row triple hit brick?
10. make paddle a separate class
11. paddle wrap around screen penalty
12. when no games were saved previously, display label animation for it
13. clean collision detection! [DONE]
14. menupage and score can be singleton class
15. check design patterns and implement it
16. sound effects?
17. gold time progress bar?
18. delet unecessary files from github [DONE]
19. tutorial page
20. levels page? maybe keep track of levels unlocked?
21. restart game and new game have similar implementations can simplify it
22. make MVC!
22. animation class, can remove the remove label function and use it in View class
22. pause button
23. CHECK IF INITBALL INITBREAK AND INITBBOARD ARE IN ONINIT FUNCTION