# COMP2042_CW_hcyam4

## Compilation Instructions:
Provide a clear, step-by-step guide on how to compile the
code to produce the application. Include any dependencies or special settings
required.
## Implemented and Working Properly: 
List the features that have been successfully
implemented and are functioning as expected. Provide a brief description of each. 
### Problems solved and simple refactoring
1. UnsupportedThreadException occurred in the stop() function in the GameEngine class. This was solved by utilizing the interrupt() function instead of the stop() function for all the 3 different threads; physicsThread, updatethread and the timeThread.
2. InterruptedException occurred in all the overridden run() functions of the GameEngine class. This was fixed by placing return statements in the catch blocks.
3. Load button was created in the Main class but wsa not visible. Added it to the root of Pane().
4. Altered numerous meaningless identifier names to meaningful ones such as scoreIncrement, message, msg, etc.
5. NullPointerException occurred when saving the state of the game. This was fixed by using the assert statement to prevent the null pointer from the outputStream.flush() function in the saveGame() method of the LoadSave class.
6. The save directory that was created was always saved in local disk C. The location was changed to the relative path of the project directory for ease of access and because it is best to store the related files in the same directory.
7. The Initialise method in the GameEngine class only called one function (onAction,OnInit()), so I removed this method and directly called the function.
8. The label animations would freeze and remain on the screen. This was because there was a new thread being created for every label to be animated. This issue was fixed by placing the animation section of the code into PLatform.runLater(). It is common practice to wrap UI components inside the Platform.runLater().
9. Meaningless identifier labels were changed to gameOverLabel and winLabel. 
10. There was an IO Exception when there are no existing saved games. Handled this by outputting that no games were saved and removed the print stack trace.
11. Made direct assingments to goRightBall and goDownBall in setPhysicsToBall in Main class instead of assigning to colideToRightBlock, colideToTopBlock, colideToBottomBlock and colideToLeftBlock and finally assigning to goRightBall and goDownBall.
12. I have used lambda expressions for anonymous methods in Platform.runLater() calls.
13. I have transferred the load and save logic from the Main class to the LoadSave class. It is more appropriate that it is in this class than in the Main.
14. Because of transferring the load and save to the LoadSave class, I have removed the read function. This function previously served as the intermediary function but now since all the data is streamed directly into the load function, there was no need of the read function.
15. Since I have changed the graphics of the game, the identifier choco was not appropriate, so I changed it to bonus or bonusObj in some cases.
16. Changed name of move function in GameController class to movePaddle because it is not specific enough since there are other moving objects in the game.
17. Since, I have decided to initalise the bricks with one colour, some unnecessary lines of code was removed from the Block class like creating new Image and new Image patterns. 
18. The logic for the ball initialising was flawed since it was initialised to a random position and always started by moving downwards. From a players perspective this is unfair, because there can be cases when the position can be at the bottom wall where the player would immediately lose a life before starting to play. Because of this, I removed the randomly generated positions and initalised it so that it will start moving upwards from the center of the break paddle.
19. To prevent unnecessary modifications to the score attribute of the Score class, I have set it to private and added getter and setter methods for it.
20. named restartGame to gameReset as it resets the complete game and the level will being from level 1. This can be easily confused with retry game.
21. Significantly reduced the number of lines and improved code readability by using the ternary operator for conditional statements like for assigning goRightBall and goLeftBall.
22. Moved the initBoard function to the Block class and made it static so that it would not be included in instances of the Block class.
23. Made the collision detection for the blocks and the break paddle with the ball better by including the ball radius into the comparisons.
24. When the ball would hit the break at the center, the ball would move really slowly. It only moved faster when it hit the edges of the break. I improved the ball movement by changing the way it was coded to move by removing the relations logic method of calculating the vX and vY. Instead, I just set the vX and vY to a fixed value in setPhysicsToBall() function and incremented the ball x and y conditions. This issue was caused by the balls speed that would depend on the current level, break and ball position.
25. Did not use the oldXBreak variable, so I removed it.
25. Stopped the game after each level, displayed either winMenu, betHighScoreMenu, or gameOverMenu. The player can then choodse whether to proceed to the next level, retry the current level or go back to menu.
26. When the player retries a level, heart and score is reinitialised to 3 and 0.
27. When the player proceeds to the next level, heart and score will not change.
28. When the player goes back to menu, the statistics are reset and the heart, score and level are reinitialised.
29. 



## rough updates
made the {LIST THE DIFFERENT VARIABLES} protected static so that it can be accessed directly
from other classes and can be manipulated without using temporary variables like in LoadSave class
made Animation class to add the animations to the different labels and to remove the label
added heart animation
added debris animation
added soundeffects
------
fixed load game
fixed score heart label by using text wrap
------
3 level brick breakability works now
------
made setPhysicstoball shorter by separting the wall and break collisions into 2 other functions and calling them
made start function shorter by doing the displays in menuPage constructor
made loadgame.setonaction shorter by making new function called checkLoad for bobs coding conventions because it
had more than 5 indents
added new functoins to displayview class for adding and removing gold status views
added new function to bonus called checkistaken
added highscore functionality using file handling
moved some functions to bonus class, block class when making functions in main class shorter
------
added exit game using ESCAPE key
added pause resume button using the P key
applied new penalty in bonus class, it makes the break wrap around the screen added to loadSave
------
created separate branch called addSound
added sound effects


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
2. highscore [DONE]
3. fix the menu which does not show [DONE]
4. DO PLAY HEART ANIMATION [DONE]
4. fix the load game, nothing shows up [DONE]
3. css design alignment of all the buttons [DONE]
4. main game title in main menu [DONE]
6. fix nullpointerexception error [!!!!!!!!!!!]
7. fix concurrentexception error [DONE]
8. 3 levels of brick breakability [DONE]
9. fix the retry function it works for levels >1 and backtomenu also does not work for levels >1[DONE]
11. paddle wrap around screen penalty [DONE]
12. when no games were saved previously, display label animation for it [DONE]
13. clean collision detection! [DONE]
14. menupage and score can be singleton class
15. check design patterns and implement it
16. sound effects? [DONE]
17. gold time progress bar?
18. make DISPLAYVIEW singleton
18. delet unecessary files from github [DONE]
19. tutorial page
21. restart game and new game have similar implementations can simplify it [DONE]
22. make MVC!
22. pause and exit button [DONE]
24. wrap all ui components in platform.runlater() for removing the nullpointer exception if this does not work its ok just mention in readme








## Implemented but Not Working Properly: 
List any features that have been
implemented but are not working correctly. Explain the issues you encountered,
and if possible, the steps you took to address them.
## Features Not Implemented: 
Identify any features that you were unable to
implement and provide a clear explanation for why they were left out.
## New Java Classes: 
Enumerate any new Java classes that you introduced for the
assignment. Include a brief description of each class's purpose and its location in the
code.
## Modified Java Classes: 
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.
## Unexpected Problems: 
There will always be the NullPointerException that comes unexpectedly and the game freezes. This issue was detected early on, but it was impossible to solve.
Though, from my knowledge, I assume that it is due to the root Pane(), when it unexpectedly becomes null during gameplay.
Another issue was when the ball would freeze on screen, but it would be moving in the background and break the blocks. This can be shown by the scores increasing and the hearts decreasing in the game statistics. This may just be an issue with my computer.
It took a while to figure out whether to dispose the mediaPlayer or to restart it, because the sound effects would get garbled and the sound would not stop. This was fixed by creating and initialising the media player in a separate function before playing it. 
When going to the next level, or when a game needs to be retried, or when the player wishes to go back to menu,even if the buttons had been set to an action in the start method, the buttons never called the assigned methods.I had to set the functions of the buttons at every state game where the buttons were to be visible for them to work otherwise the buttons never function.
Sometimes, the labels that are displayed are frozen on screen, this is not an error. It is just the computer processing problem. This is usually a problem that occurs at the beginning of the game when you first start playing it. This lagging is mainly due to the animation and sound effects applied to the game.


Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them.




## Refactoring


