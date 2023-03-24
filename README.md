# 🎲 __PARCHEESI__ 🔵🟡🟢🔴
## A digital version of the classic 4 player game with the same name but with only 1 die [(wiki/Parcheesi)](https://en.wikipedia.org/wiki/Parcheesi). Learning project in Java.
___

![GitHub contributors](https://img.shields.io/github/contributors/DRBondyaleJuez/Parcheesi)
![GitHub repo size](https://img.shields.io/github/repo-size/DRBondyaleJuez/Parcheesi)
___

## __DESCRIPTION__
This code was used as a training exercise to practice coding in java. 
The design pattern followed by the code is based on the Model-View-Controller [(MVC)](https://developer.mozilla.org/en-US/docs/Glossary/MVC) or more precisely Model-View-ViewController-Controller pattern.

The image assets present in the view are handled in a persistence package by a singleton class which communicates with the controller and another class which makes petition storage.

The view is managed using the JavaFXML framework [(MVN repository link)](https://mvnrepository.com/artifact/org.openjfx/javafx-fxml).

<div style="text-align: center;">

![Diagram](https://user-images.githubusercontent.com/98281752/227616769-f5b41129-7862-455e-9e0d-9f717330430f.png)

</div>

___
___

## __USAGE__
This is a simple game designed while learning how to code with Java. Four players are needed to play this game of Parcheesi no single player mode has been implemented. To help a proper course of the game a section of the view is a textArea destined to provide instructions and sugest the following actions.

<!-- OL -->
- Player 1 begins. The turns go clockwise. The die is rolled either clicking on it or clicking on the button. A piece does not enter the game until a 5 is rolled. When a player has any piece on the board after rolling the die it must click on the piece the player wants to move. the pieces move clockwise following the white squares until it reaches the entrance of the centre squares of its color.


- When a 6 is rolled after moving that same player can roll again and select another piece to move. If a 6 is rolled 3 times the most advance piece of that player returns to before entering th board.


- The squares with a circle in the centre are safe which means two piece of different players can be on this square. In any other square if a piece ends on a square where there is another piece of another player the piece that was there is removed and the player that reached the square can select any of their piece to advance 20 squares.


- If two pieces of the same player in a normal square or two piece of different players are in the same square they form a barrier and no piece can go through. A player is forced to break a barrier if it doesn't have any other piece to select.


- The movement in the last coloured squares is slightly different. To reach the end the player must roll the exact number any excess will cause the piece to bounce back up to the initial coloured square. When a piece reaches the end that player has to select another piece to move 10.A player wins if all its pieces reach the end.


- The game can be restarted at any moment clicking th "Start New Game" button.


The program evaluates if after rolling the die the piece selected to move corresponds to the player that rolled the die. When a player's turn is finished it jumps to the next player. The instruction section is constantly displaying helpful information to guide.

<div style="text-align: center;">

![gameGIF](https://user-images.githubusercontent.com/98281752/227627120-2130486f-02bb-452d-a0dc-dcf9ebee8b27.gif)

</div>

___
___

## __INSTALLATION INSTRUCTIONS__
### __For IDE:__
<!-- OL -->
1. Clone the repository in your local server
2. Run the project's Main Class in your IDE

### __For Ubuntu (In terminal):__
<!-- OL -->
1. If necessary [install java version 11 or higher](https://stackoverflow.com/questions/52504825/how-to-install-jdk-11-under-ubuntu)


    ```bash 
        sudo apt-get install openjdk-11-jdk
    ```


2. If necessary [install maven version 3.6.3 or higher](https://phoenixnap.com/kb/install-maven-on-ubuntu)

   ```bash 
       sudo apt install maven
   ``` 

3. If necessary [install git](https://www.digitalocean.com/community/tutorials/how-to-install-git-on-ubuntu-20-04)

   ```bash 
       apt install git
   ```

4. Clone the repository

   ```bash 
       git clone https://github.com/DRBondyaleJuez/Parcheesi.git
   ```

5. Go to the project folder. Make sure the [pom.xml](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) is there.

6.  Create [.jar file](https://en.wikipedia.org/wiki/JAR_(file_format)) executable in target folder using the following code:

    ```bash
		mvn install 
    ```

7. This code uses javafxml so we recommend the use of the following code  to run the program :

   ([*Source*](https://github.com/openjfx/javafx-maven-plugin))

   ```bash 
       mvn javafx:run
   ```

___
___
## __INSTRUCTIONS FOR CONTRIBUTORS__
The objective of the project was to practice and apply java knowledge. No further contributions will be need all of this is just a training excercise.

Hope you may find the code useful and please acknowledge its origin and authorship if use for any other purpose.





