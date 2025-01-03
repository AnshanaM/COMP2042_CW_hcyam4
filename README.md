
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

### **7. using POM.XML:**
- open the pom.xml file to ensure all the dependancies are downloaded
  
  
## Implemented and Working Properly:   
List the features that have been successfully implemented and are functioning as expected. Provide a brief description of each.
### Block break-ability
Each blocks break-ability is dependent on the speed of the ball and the number of times it is hit to a maximum of 3 upon which it will be destroye. After every single hit, the image of the block will change to a random image. BLOCK_NORMAL are initialised to concrete blocks where as the BLOCK_STAR, BLOCK_HEART and BLOCK_CHOCO are initialised to red blocks.
### Break Paddle Teleportation
There is a new bonus called teleport which upon activation will allow the break paddle to wrap around the screen when reaching the left or right walls. For example, if the break paddle is moved to the utmost left and xBreak = 0, if the player presses LEFT again, the break paddle will be teleported and emerge from the right wall and vice-versa.
### High score
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
When the player beats the high score and goes to the next level, the gameplay stats don't show the new high score, the heart or the level number. But it shows the score. When the program moves control to the start function it should clear all objects in the root and then add them again. I have tried to explicitly remove it from the root and adding it again but that also did not work.


  
## Features Not Implemented
Identify any features that you were unable to  
implement and provide a clear explanation for why they were left out.
  
While exploring various features for my game, I considered including additional bonuses like a long paddle, a gun, and potential power-ups. However, upon careful consideration, I decided not to implement these features as they would significantly impact the game's difficulty and strategic depth. Implementing features like a long paddle or a gun could significantly simplify the gameplay, making it too easy for players to score and progress. 
Instead of implementing features that would simplify the game, I opted for a unique penalty called "teleportation." This feature adds a layer of unexpectedness and challenge, requiring players to adjust their strategies and adapt to the sudden changes in the ball's position. This approach maintains a balance between difficulty and enjoyment, promoting replayability and encouraging players to develop new skills.
  
## New Java Classes
Enumerate any new Java classes that you introduced for the  
assignment. Include a brief description of each class's purpose and its location in the  
code.  
New Java classes that were created include DisplayView, GameController and SpecialEffects.
The DisplayView class plays the role of the View in the MVC pattern by managing the user interface components, handling their dynamic updates, and controlling their visibility based on the game state. It provides a clear separation between the presentation logic and the underlying game logic.
The GameController class serves as the Controller component in the MVC pattern by managing user input, coordinating actions between the View and Model, and providing a centralized control point for various aspects of the game's behavior.
The SpecialEffects class centralizes the management of visual effects in the game, including label animations, object initialization, heart animations, block debris animations, sound effects, and handling ImageView objects within a specified Pane.

## Modified Java Classes
List the Java classes you modified from the provided code  
base. Describe the changes you made and explain why these modifications were  
necessary. 

Block class: I changed the logic for assigning the colors to the blocks in the draw() method. This was to make the code shorter and remove duplication of code because ImageView was initialised every time each condition was checked. Instead I set the appropriate resource url to the attribute color and then after checking each condition, I assigned the color to the ImageView using fillPattern.
I also moved a function called checkHitToBlock() from the Main class to Block class because this method needs to be called to check for every instance of the block that is created. This function was actually created from the OnUpdate function in the Main class.
I also created a new method called setBlockFill() because it is needed when updating the color of the block after every hit.
I moved InitBoard from Main to Block and made it static so that it wouldn't be created for every single instance of the Block class.
The last method is blockIsHit() which was also derived from the OnUpdate() function from the Main class. This was because this function needs to be called for every instance of the block.
Concluding the Block class, it contains some functions from the Main class that was associated with the Block class and reduced significant complexity of the OnUpdate function in the Main class.

Bonus class: Added a new Bonus called teleport. I added the image url to the draw method. Also I moved the logic of checking if the bonus was taken by the break paddle, from the onPhysicsUpdate() function and called it checkIsTaken.

LoadSave class: There was significant amount of changes made to this class as alot of variables were removed. This is because I moved the load function from the Main class into this class. There was no need of the read function also in the load class because it only served as an intermediary (as did the multiple variables). Hence a significant amount of code was removed from Main because of this modification. 

Main class: Apart from the functions moved out of the Main class, the start function was split into 2 more functions to aid readability and easier understanding of the program. The initStage and the checkLoad functions are used by the start method. The handle function was modified to call another function from the gameController class because it needs to fetch the inputs from key events from the gameController class. SetPhysicsToBall was also split into more functions called checkWallCollisions and checkBreakCollisions. This demonstrates the separation aspect of refactoring. Making the code easy to read and comprehend.
There were not any logical changes made to checkDestroyedCount, only visual effects were added and sound effects with the help of the DisplayView and the SpecialEffects class. RestartGame() now called resetGame(), and nextLevel() methods were shrunk by finding common implementations and grouping those together. OnUpdate() had the most changes as it was split into 4 functions, called updateGameRunningObjs (which updated positions of breaks, ball and bonuses by using the DisplayView class), updateBallDirection() updates th =e status of the ball direction variables, isBlockColidingWithBlocks checks the main condition for ball and block collision and finally the handleBlockHit function includes necessary logic to handle what happens after a block is hit (like checking the number of hits and whether it is destroyed).
OnPhysicsUpdate was made shorter by moving the logic of checking if the bonus was taken which is now handled by the Bonus class.

Score class: Since score was made private because it shouldn't be modified explicitly, getters and setter functions were made. Since highscore functionality was also added, getter and setter for highscore was also created. Functions for reading the highscore from the saved file, checking the highscore against the current score, doing the necessary updates to the file and creating the highscore text file were created in this Score class.



## Unexpected Problems
Communicate any unexpected challenges or issues you  
encountered during the assignment. Describe how you addressed or attempted to resolve them.  

There will always be the NullPointerException that comes unexpectedly and the game freezes. This issue was detected early on, but it was impossible to solve. Though, from my knowledge, I assume that it is due to the root Pane(), when it unexpectedly becomes null during gameplay.  
Another issue was when the ball would freeze on screen, but it would move in the background and break the blocks. This can be verified by the scores increasing and the hearts decreasing in the game statistics. It is important to note that this may be an issue on the computer I tested on. 
It took a while to figure out whether to dispose the mediaPlayer or to restart it, because the sound effects would get garbled and the sound would not stop. This was fixed by creating and initialising the media player in a separate function before playing it.   
When going to the next level, or when a game needs to be retried, or when the player wishes to go back to menu,even if the buttons had been set to an action in the start method, the buttons never called the assigned methods.I had to set the functions of the buttons at every state game where the buttons were to be visible for them to work otherwise the buttons never function.  
Initially, some labels that are displayed are frozen on screen. This is usually a problem that occurs at the beginning of the game when you first start playing it. This lagging is mainly due to the animation and sound effects applied to the game.  
When loading from the file, there was a ConcurrentModificationException because the blocks array was being altered while being iterated through it. This was solved by using a different array to load the blocks into and then loading it to the main blocks array.


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
 - Used ternary operator for conditional statements to improve code readability.
 - used constants called RETRY, NEXT and BACK to denote different menus. This follows Bob's coding conventions.

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
 - Upon retrying a level, the heart and score attributes are reinitialised; on proceeding to the next level, they remain unchanged; on going back to menu, the statistics are re-initialised.
### Singleton Classes:
 -  Score: 
Single source of truth: There's only ever one score in the game, and having it as a singleton ensures consistent access and updates from anywhere in the code.
 -  DisplayView: 
Centralized UI control: By having a single DisplayView instance, you ensure consistent management of buttons, background image, and other UI elements. This simplifies logic and avoids redundancy.
 -  SpecialEffects: 
Resource efficiency: Media players and sound effects are often resource-intensive. Having a single instance managed by a singleton SpecialEffects class ensures efficient usage and avoids having multiple players competing for resources.
 -  LoadSave: 
Data consistency: Save/load operations are critical for maintaining game progress. A singleton LoadSave class guarantees consistent access to the save file and avoids potential data corruption from multiple instances.
 -  GameEngine: 
Thread management: Game engines often involve multiple threads for animations, physics, and other tasks. A singleton GameEngine simplifies thread management and ensures coordinated execution without conflicts.

### MVC Pattern
I have implemented a concrete MVC pattern, where the Main class is the Model, the DisplayView class is the View and the GameController is the Controller.

The Main class is the Model class because of the following reasons:
- Data Storage: The class holds various data members that represent the state of the game, such as the level, positions of the ball and break paddle, the number of hearts, the score, and other game-related parameters.
- Game Logic: Methods like setPhysicsToBall(), checkWallCollisions(), checkBreakCollisions(), checkDestroyedCount(), handleBlockHit(), and others handle the core game logic, physics, and collision detection.
- State Handling: The class manages the state of the game, including the level transitions, game resets, and updates to game objects.
- Interaction with View: The class interacts with the DisplayView class to update the UI based on the game state. It utilizes Platform.runLater() to safely update the JavaFX UI elements.
- Event Handling: The class implements the EventHandler<KeyEvent> interface, indicating its capability to handle user input events.
- Singleton Instances: The class holds singleton instances of other classes (DisplayView, Score, GameEngine, LoadSave, SpecialEffects, GameController), suggesting a centralized point for managing and coordinating these components.
- Callback Methods: The class implements the OnAction interface with callback methods like onUpdate(), onInit(), onPhysicsUpdate(), and onTime(). These methods are invoked by the GameEngine during specific events in the game lifecycle.
- Encapsulation of Game Elements: Game-related elements like Block, Bonus, and others are encapsulated within the class.

The GameController class is the controller class because the following reasons:
- User Input Handling: The class contains a handle(KeyEvent event) method that handles user input events, specifically key events. This method interprets keyboard inputs and takes appropriate actions in response to the user's commands. 
- Intermediary Between View and Model: The GameController class acts as an intermediary between the user interface (View) and the game logic (Model). It translates user inputs into actions that affect the game state and communicates with other parts of the program accordingly. 
- Encapsulation of User Actions: User actions such as moving the paddle, saving the game, handling pause/resume, and exiting the game are encapsulated within the GameController class. This encapsulation ensures that the logic related to user input is concentrated in a single class, promoting maintainability. 
- Singleton Pattern: The class follows the Singleton pattern, ensuring that there is only one instance of GameController throughout the application. This centralized control contributes to consistency in handling user input and game events. 
- Interaction with Other Components: The class interacts with various components of the application, such as the Main class (containing game state and engine), DisplayView (UI components), and other utility classes. It coordinates actions between these components based on user input. 
- Game Lifecycle Management: The GameController class is responsible for managing aspects of the game lifecycle, including starting the game engine, resetting the game state for different scenarios (retrying, going back to the menu, advancing to the next level), and checking conditions for level completion.
- Decoupling of UI and Game Logic: By handling user input and translating it into actions without directly manipulating the game state, the GameController class helps maintain a separation of concerns. This decoupling allows for easier modification of the UI or game logic independently.

The DisplayView class is the View class because of the following reasons:
- User Interface Representation: The class is responsible for creating and managing the visual representation of the user interface. It includes components such as buttons, labels, and images that make up the game's display. 
- Separation of Concerns: The class encapsulates the visual elements and layout configurations of different screens, such as the main menu, game over menu, win menu, and gameplay statistics. This separation of concerns allows for a clear distinction between the visual presentation and the underlying game logic. 
- UI Initialization: The constructor of the class initializes various UI components, such as buttons, labels, and images. It sets up the initial appearance of the game screens.
- Singleton Pattern: The class follows the Singleton pattern, ensuring that there is only one instance of DisplayView throughout the application. This centralized control of the UI elements promotes consistency in the presentation of different game screens. 
- Dynamic UI Updates: The class provides methods to update the displayed information dynamically, such as updating the score, level, and heart count during gameplay. This reflects changes in the game state on the user interface. 
- User Interaction Handling: The class defines methods to handle user interactions, such as button clicks. For example, it specifies actions to be taken when buttons like "LOAD GAME," "NEW GAME," "TUTORIAL," and others are clicked. 
- Display Changes and Animations: The class includes methods for displaying messages and animations on the screen. It handles the presentation of messages like "GAME PAUSED," "YOU WIN," and "GAME OVER," as well as score increment animations. 
- UI Transition and Visibility Control: The class manages the transition between different game screens (main menu, game over, win, etc.) by controlling the visibility of the corresponding UI elements. It provides methods like setBackToMenu, setGameOver, setGamePlay, etc., to switch between different screens. 
- Font Loading: The class handles the loading of a custom font, indicating its involvement in the visual styling of the UI.