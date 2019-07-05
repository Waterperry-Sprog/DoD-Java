========Dungeons of Doom================

This is the readme for the Dungeons of Doom text-based game.

INSTALLATION
=============================================================================================================
In the game folder, you will find a 4 .java files and this readme file.
Take note of the path to the game files as you will need this for defining custom maps.
	- (if you do not know the path, use the UNIX command "pwd" (Windows - "cd") to print the directory.)
	- (make sure that you are in the folder with the .java files when you run this command.)
Launch a CMD (Windows) or Terminal (Mac/Linux) window in the directory with the .java files.
Compile the game files using "javac *.java". Do not touch the .class files produced.
Run the game with the command "java GameLogic".

GAMEPLAY - Setup
=============================================================================================================
You will be greeted with a screen prompting you for either a filepath for the map you wish to use, or just to
press enter to play with the small default map "Very Small Labyrinth of Doom". Upon completing this step, you
will be spawned into a random place within the walls of your map.
On the map there are different tiles:

#	wall tile. you cannot traverse these.
.	empty floor tile. you can traverse these.
E	an exit tile. you can traverse these. Running the 'QUIT' command on this square with sufficient gold will
	result in a 'win'.
P	player tile. this is you.
B	bot tile. this is the bot chasing you.
G	gold tile. you can traverse these. Running the 'PICKUP' command on this square will increase the player's
	gold count by 1.

The goal of the game is to pick up enough gold to exit the map alive. When you spawn, a bot will also spawn.
The bot will attempt to chase you around the map and kill you (if the bot ends up on the same square as you
at the end of its turn, you will be killed). 

On your turn, you will be prompted for an input. You have to enter a command from the list of commands below.
If the command completes successfully, you will receive either a response or "SUCCESS" depending on the 
command you chose.

USING A CUSTOM MAP
=============================================================================================================
If you wish to use a custom map with the game, please ensure it is a .txt file formatted as follows:
On line 1, there should be the map name in the format: "name NAME OF MAP"
On line 2, there should be the gold required to win the game in the format: "win xxx"
	Note this doesn't have to be a three digit number; it can be any number.
On lines 3-->, there should be a map file consisting of only legitimate map characters (see above - Setup).
When prompted, enter the full file path for the file, including the .txt extension.


GAMEPLAY - Commands
=============================================================================================================
HELLO   Prints the number of gold required to leave map.
GOLD    Prints the players current gold count.
MOVE    Moves the player in the specified direction (UP/DOWN/LEFT/RIGHT)
LOOK    Displays the map immediately surrounding the player.
PICKUP  Picks up the gold if there is any on the players square.
QUIT    Exits game: win if the current square is an exit and the correct
		amount of gold is held; lose otherwise.
HELP	Displays all available commands

GAME CODE IMPLEMENTATION
=============================================================================================================
Classes		GameLogic, HumanPlayer, Bot, Map.

GameLogic	handles player/bot commands such as move(), look(), as well as the main() method. This class 
			creates all player objects and the map object.

HumanPlayer	handles getting the player's next move, determining where the player is in the map, and how much
			gold the player has.

Bot			a subclass of HumanPlayer so that the bot inherits methods such as getPosition(). The overridden 
			method getNextCommand() determines the bot's next move based on whether the bot has recently seen
			the player, and if it hasn't then the bot moves randomly or looks.

Map			handles the importing of new maps, setting the mapfile in GameLogic to the default map or a user-
			defined map, finding spawn points for players and bots, and handling the retrieval of individual
			tiles from the map. The code also defines a default map if no custom map is desired.