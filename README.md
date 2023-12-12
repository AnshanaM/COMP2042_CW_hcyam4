
# COMP2042_CW_hcyam4

## Compilation Instructions:
### **1. Prerequisites:**
-   Install Java Development Kit (JDK) 19.0.2
- Install JavaFX SDK 21.0.1
-   Download and install IntelliJ IDEA or any Java IDE.
-   Ensure you have Maven set up in IntelliJ.

### **2. Open the Project in IntelliJ:**

-   Open the project folder containing the Java files and pom.xml in IntelliJ.
-   If prompted, select "Import Project" and choose "Maven" as the project type.

### **3. Verify JavaFX Library:**

-   Open Project Structure (File > Project Structure).
- Go to "Libraries" and click the "+" button and select "Java".
- Find the Java directory in Program Files and select "lib" inside the JavaFX SDK directory.
- Apply the changes.


### **4. Mark Resources Folder:**
-   Open the project directory in your IntelliJ file explorer.
-   Locate the folder containing the game assets (images, sounds, etc.).
-   Right-click the folder and select "Mark Directory as > Resources Root".

### **5. Build and Run the Program:**
-   In IntelliJ, navigate to the "Run" tab (Run > Run).
-   Choose the "Main" class file as the configuration.
-   Click the "Run" button (green arrow icon) or press Shift + F10.

### **6. Troubleshooting:**
-   Verify that all dependencies, particularly the `javafx` library, are available


## Implemented and Working Properly:
List the features that have been successfully implemented and are functioning as expected. Provide a brief description of each.
### Block breakability
Each blocks breakability is dependent on the speed of the ball and the number of times it is hit to a maximum of 3 upon which it will be destroye. After every single hit, the image of the block will change to a random image. BLOCK_NORMAL are initialised to concrete blocks where as the BLOCK_STAR, BLOCK_HEART and BLOCK_CHOCO are initialised to red blocks.
### Break Paddle Teleportation
There is a new bonus called teleport which upon activation will allow the break paddle to wrap around the screen when reaching the left or right walls. For example, if the break paddle is moved to the utmost left and xBreak = 0, if the player presses LEFT again, the break paddle will be teleported and emerge from the right wall and vice-versa.
### Highscore
Using simple file handling, I record the highest score in a text file stored relative to the path of the project directory. When the player plays the game, after they *win* a game, their current score will be compared with the highscore and appropriate output will be displayed and the updates will be made to the file if highscore is bet. When the player then proceeds to the next level, the current score is not re-initialised to 0. It will remain the same and the next levels score will be added to it. Hence, after every level the players score will keep increasing until the end of the game (after 7 levels). If the player loses in a level, the score is erased and the streak is lost, but if they win a level, the streak continues and checks are made to see if the player bet the highscore. If the player beats the highscore at any level, they win the level. If the player beats the highscore by the end of all the levels, the player is the true winner of the game.
### Heart Animation
Added heart animation, where when the block of BLOCK_HEART is destroyed, the heart image will pop up, pulse 3 times and then move to the heart label of the game statistics menu.
### Debris Animation
Added block debris animation, where when each block is destroyed, the debris will pop up and change opacity and will disappear in the position of the block. This gives a realistic feel to the game when destroying the blocks.
### Pause/Resume Game
Implemented the pause and resume of the game which are both activated using the *P* key.
### Exit Game
The game can be exited *during* game play using *ESC* key. This is implemented by exiting the game engine and closing the window. Note, this only works when the game engine is running, it will not work when the main menu is displayed. This is not an issue since the window's close button can be used. One may argue that the game can be exited by just using the application window close button but it takes time to close the game engine. It is considerably quicker to close the engine and the window when closing the application while it is running.
### Sound Effects
This game has different sound effects for interactions between game play objects and for the game over and win menus. Different sounds are played when the ball hits the wall, when the ball hits the bottom wall, when ball hits a block, and when the game over menu is shown and when the you win menu is shown.
### Window icon
The logo image of the game Brick by Brick is placed as an icon for the window. This shows in the application window icon and in the taskbar.
###  External font
For all the labels and buttons in this game I have used an external font called VT323. I used this font because it suits the pixelated aesthetic of this game. There is no need to import any library to use this font because I have included the the font source in the resources directory.

## Implemented but Not Working Properly:
List any features that have been  
implemented but are not working correctly. Explain the issues you encountered, and if possible, the steps you took to address them.

I used VBox and HBox for the menus I have implemented called mainMenu, winMenu, gameOverMenu, betHighScoreMenu and also a game statistics menu called gamePlayStats. When initialising the buttons. particularly the back to menu, retry and next level buttons, they need to be setOnAction() to a function during runtime else the associated functions are not called. Because of setting them during run time, the only way it would work would be when I remove it from root Pane(), set the function and then add it to the root Pane again. This is very tedious but it is the only way I could make the buttons work. Because of this issue, some functions in the Main class are long.


## Features Not Implemented
Identify any features that you were unable to  
implement and provide a clear explanation for why they were left out.

While exploring various features for my game, I considered including additional bonuses like a long paddle, a gun, and potential power-ups. However, upon careful consideration, I decided not to implement these features as they would significantly impact the game's difficulty and strategic depth. Implementing features like a long paddle or a gun could significantly simplify the gameplay, making it too easy for players to score and progress.
Instead of implementing features that would simplify the game, I opted for a unique penalty called "teleportation." This feature adds a layer of unexpectedness and challenge, requiring players to adjust their strategies and adapt to the sudden changes in the ball's position. This approach maintains a balance between difficulty and enjoyment, promoting replayability and encouraging players to develop new skills.

## New Java Classes
Enumerate any new Java classes that you introduced for the  
assignment. Include a brief description of each class's purpose and its location in the  
code.


## Modified Java Classes
List the Java classes you modified from the provided code  
base. Describe the changes you made and explain why these modifications were  
necessary.


## Unexpected Problems
Communicate any unexpected challenges or issues you  
encountered during the assignment. Describe how you addressed or attempted to resolve them.

There will always be the NullPointerException that comes unexpectedly and the game freezes. This issue was detected early on, but it was impossible to solve. Though, from my knowledge, I assume that it is due to the root Pane(), when it unexpectedly becomes null during gameplay.  
Another issue was when the ball would freeze on screen, but it would move in the background and break the blocks. This can be verified by the scores increasing and the hearts decreasing in the game statistics. It is important to note that this may be an issue on the computer I tested on.
It took a while to figure out whether to dispose the mediaPlayer or to restart it, because the sound effects would get garbled and the sound would not stop. This was fixed by creating and initialising the media player in a separate function before playing it.   
When going to the next level, or when a game needs to be retried, or when the player wishes to go back to menu,even if the buttons had been set to an action in the start method, the buttons never called the assigned methods.I had to set the functions of the buttons at every state game where the buttons were to be visible for them to work otherwise the buttons never function.  
Initially, some labels that are displayed are frozen on screen. This is usually a problem that occurs at the beginning of the game when you first start playing it. This lagging is mainly due to the animation and sound effects applied to the game.


## Refactoring
### Exceptions:
- UnsupportedThreadException: Occurred in the stop() function in the
  GameEngine class. Solved by using the interrupt() function instead of
  stop() for all three different threads (physicsThread, updateThread,
  and timeThread).
- InterruptedException: Occurred in all the overridden run() functions of the GameEngine class. Fixed by placing return statements in the catch blocks.
- NullPointerException: Occurred when saving the state of the game. Fixed by using the assert statement to prevent the null pointer from the outputStream.flush() function in the saveGame() method of the LoadSave class.
- IOException: when there are no existing saved games, handled this exception by outputting a relevent message and remaining control in main menu.

### Simple Refactoring:
- Altered meaningless identifier names to meaningful ones.
- The save directory was changed to the relative path of the project directory.
- Renamed the initialise method in the GameEngine with camel case following Bob's coding conventions.
- Renamed meaningless identifiers to gameOverLabel and winLabel.
- Made direct assignment to goRightBall and goDownBall in setPhysicsToBall in the Main class.
- Renamed restartGame function to gameReset as it can be confused as retry game. This function reloads the whole game.
- Used ternary operator for conditionaly statements to improve code readability.

### UI Improvements:
- Placed the animation and display sections of the code into Platform.runLater() for better UI control.
- Used text wrap for the layout of the game statistics to prevent shifting out of frame when the stats increase in digits.
-  Adjusted the time assigned for the label animation because they would freeze on screen during gameplay.
- Made the window to be of fixed size with height sceneHeigt and sceneWidth. This window cannot be resized.

### Game Statistics and Objects:
- Ball is initialised to start from the break paddle and always start movement upwards not downwards. This is because, in accordance to the previous logic, the ball would start at random positions and move downwards, so, if there was a case where the random position was the bottom wall, the player would lose a life unnecessarily.
- Set the Score class's score attribute to private and added getter and setter methods.
- Moved initBoard from Main class to Block class and made it static.
- Improved the collision detection for block and break paddle with the ball by considering the ball's radius.
- Improved ball movement by setting vX and vY to fixed values in setPhysicsToBall instead of relying on relations logic with the break and ball positions, and the current level.
- Removed unused variable oldXBreak.
- Stopped the game after each level, displaying either winMenu, gameOverMenu or betHighScoreMenu.
- Upon retrying a level, the heart and score atttributes are reinitialised; on proceeding to the next level, they remain unchanged; on going back to menu, the statistics are re-initialised.