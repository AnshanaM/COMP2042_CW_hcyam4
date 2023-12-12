
# COMP2042_CW_hcyam4  
  
## Compilation Instructions:  
Provide a clear, step-by-step guide on how to compile the  
code to produce the application. Include any dependencies or special settings  
required.  
  
  
## Implemented and Working Properly:   
List the features that have been successfully  
implemented and are functioning as expected. Provide a brief description of each.   
### Problems solved and refactoring  
Exceptions:  
 - UnsupportedThreadException: Occurred in the stop() function in the
   GameEngine class. Solved by using the interrupt() function instead of
   stop() for all three different threads (physicsThread, updateThread,
   and timeThread).  
 - InterruptedException: Occurred in all the overridden run() functions of the GameEngine class. Fixed by placing return statements in the catch blocks.  
 - NullPointerException: Occurred when saving the state of the game. Fixed by using the assert statement to prevent the null pointer from the outputStream.flush() function in the saveGame() method of the LoadSave class.
- IOException: when there are no existing saved games, handled this exception by outputting a relevant message and remaining control in the main menu.

 Simple Refactoring:
 - Altered meaningless identifier names to meaningful ones.
 - The save directory was changed to the relative path of the project directory.
 - Renamed the initialise method in the GameEngine with camel case following Bob's coding conventions.
 - Renamed meaningless identifiers to gameOverLabel and winLabel.
 - Made direct assignments to goRightBall and goDownBall in setPhysicsToBall in the Main class.

UI Improvements:
 - Placed the animation and display sections of the code into Platform.runLater() for better UI control.
 - Used text wrap for the layout of the game statistics to prevent shifting out of frame when the stats increase in digits.


  
  
## Implemented but Not Working Properly:   
List any features that have been  
implemented but are not working correctly. Explain the issues you encountered,  
and if possible, the steps you took to address them.  
I have added 4 different menus called winMenu, gameOverMenu, mainMenu, betHighScoreMenu in the displayView class. I also have the game statistics menu called gamePLayStats.  
The 4 aforementioned menus consist of button, these buttons need to   
  
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
  
## Refactoring:
