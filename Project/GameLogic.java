/**
 * Contains the main logic part of the game, as it processes.
 *
 */
 
import java.io.*; 

public class GameLogic {

	public Map gameMap = new Map(1);
	public HumanPlayer player = new HumanPlayer();
	public Bot bot = new Bot();
	private boolean isGameRunning;	
	
	/**
	 * Default constructor
	 */
	public GameLogic() {
		isGameRunning = true;
	}

    /**
	 *
	 * Checks if the game is running
     * @return if the game is running.
	 *
     */
    protected boolean gameRunning() {
        return isGameRunning;
    }

    /**
	 *
	 *Prints out the gold required to win, then exits.
     */
	 protected void hello() {
		System.out.println("Gold required: "+gameMap.getGoldRequired());
	}

	/**
	 *
	 *Converts the map from a 2D char array to a number of lines of chars, then prints it if
	 *	called for player, or processes it if called for bot.
	 *
	 *@param id : the ID of the person calling look (0 for player, 1 for bot)
	 *
	 */
	protected void look(int id) {
		String output = "";
		if( id == 0 ){
			for(int i = player.getCoord('y') - 2; i <= player.getCoord('y') + 2; i++) {
				for(int p = player.getCoord('x') - 2; p <= player.getCoord('x') + 2; p++) {
					if( i == player.getCoord('y') && p == player.getCoord('x') ){
						System.out.print(player.getPlayerTile());
					}
					else if( i == bot.getCoord('y') && p == bot.getCoord('x') ) {
						System.out.print(bot.getPlayerTile());
					}
					else{
						System.out.print( gameMap.getTile(p,i) );
					}
				}
				System.out.println("");
			}
		}
		else if( id == 1 ) {
			for(int i = bot.getCoord('y') - 2; i <= bot.getCoord('y') + 2; i++) {
				for(int p = bot.getCoord('x') - 2; p <= bot.getCoord('x') + 2; p++) {
					if( i == player.getCoord('y') && p == player.getCoord('x') ) {
						output = output + player.getPlayerTile();
					}
					else if( i == bot.getCoord('y') && p == bot.getCoord('x') ) {
						output = output + bot.getPlayerTile();
					}
					else{
						output = output + gameMap.getTile(p,i);
					}
				}
			}
			bot.processLook(output);
		}
	}
	
	/**
	 *
	 * Processes the player's pickup command, updating the map and the player's gold amount.
	 * @return If the player successfully picked-up gold or not.
	 */
	protected void pickup() {
		if( player.getTile() == 'G' ){
			player.incGold(1);
			System.out.println("SUCCESS. Gold owned: "+player.getGoldOwned());
			gameMap.setTile(player.getCoord('x'),player.getCoord('y'),'.');
			player.setTile('.');
		}
		else{
			System.out.println("FAIL. Gold owned: "+player.getGoldOwned());
		}
	}

	/**
	 * Quits the game, shutting down the application. If the player and the bot are on the same square, it results in a lose.
	 * 	If the player is on an exit tile with the correct amount of gold, and the bot is not on the same tile, it results in 
	 * 	a win.
	 */
	protected void quitGame() {
		isGameRunning = false;
		if( player.getTile() == 'E' && player.getGoldOwned() >= gameMap.getGoldRequired() 
				&& player.getCoord('x') != bot.getCoord('x') && player.getCoord('y') != bot.getCoord('y') ) {
			System.out.println("WIN \nCongratulations, you have won the game!");
			System.exit(0);
		}
		else{
			System.out.println("LOSE");
			System.exit(0);
		}
	}
	
	/**
	 *
	 *Method to move both the player and the bot around the map.
	 *@param id : int telling the method to move either bot (1) or player (0). Allows easier expansion to multiple players.
	 *@param x : the vector to change x by (allows changing move to multiple directions more easily (e.g. UP and RIGHT) if this is desired
				 in a future edition of the game).
	 *@param y : the vector to change y by (same as above).
	 *
	 */
	protected void move(int id, int x, int y) {
		if( id==0 ){
			if( (x!=0 && gameMap.getTile(player.getCoord('x') + x, player.getCoord('y'))=='#') ||
				(y!=0 && gameMap.getTile(player.getCoord('x'), player.getCoord('y') + y)=='#') ) {
				System.out.println("FAIL");
				return;
			}
			int newX = player.getCoord('x') + x;
			int newY = player.getCoord('y') + y;
			int[] newPos = {newX,newY};
			player.setPosition(newPos);
			player.setTile(gameMap.getTile(newX,newY));
			System.out.println("SUCCESS");
		}
		
		else if( id == 1 ){
			if( (x != 0 && gameMap.getTile(bot.getCoord('x') + x, bot.getCoord('y')) == '#') ||
				(y != 0 && gameMap.getTile(bot.getCoord('x'), bot.getCoord('y') + y) == '#') ) {
				return;
			}
			int newX = bot.getCoord('x') + x;
			int newY = bot.getCoord('y') + y;
			int[] newPos = {newX,newY};
			bot.setPosition(newPos);
			bot.setTile(gameMap.getTile(newX,newY));
		}
	}
	
	/**
	 *
	 *Processes the input and calls the appropriate function for either player or bot.
	 *@param input : The input string, formatted as "IDENTIFIER ACTION MODIFIER"
	 *				 The identifier for HumanPlayer is 0 and 1 for the Bot. Action can be LOOK, HELLO, GOLD etc.
	 *				 MODIFIER refers to commands such as MOVE UP where more than one command word is needed.
	 *
	 */
	protected void processInput(String input){
		if( input.charAt(0) == '0' ){
			if( input.contains("GOLD") ){
				System.out.println( "Gold owned: " + player.getGoldOwned() );
			}
			else if( input.contains("MOVE") ) {
				if( input.contains(" N") ) {
					move(0,0,-1);
				}
				else if( input.contains(" S") ) {
					move(0,0,1);
				}
				else if( input.contains(" W") ) {
					move(0,-1,0);
				}
				else if( input.contains(" E") ) {
					move(0,1,0);
				}
				else{
					System.out.println( "FAIL" );
				}
			}
			else if( input.contains("LOOK") ) {
				look(0);
			}
			else if( input.contains("HELLO") ) {
				System.out.println("Gold to win: "+gameMap.getGoldRequired());
			}
			else if( input.contains("QUIT") ) {
				quitGame();
			}
			else if( input.contains("PICKUP") ) {
				pickup();
			}
			else if( input.contains("HELP") ) {
				System.out.println("Commands - ");
				System.out.println("HELLO\tPrints the number of gold required to leave map.");
				System.out.println("GOLD\tPrints the player's current gold count.");
				System.out.println("MOVE\tMoves the player in the specified direction (N/S/E/W)");
				System.out.println("LOOK\tDisplays the map immediately surrounding the player.");
				System.out.println("PICKUP\tPicks up the gold if there is any on the player's square.");
				System.out.println("QUIT\tExits game: win if the current square is an exit and the correct amount of gold is held; lose otherwise.");
				System.out.println("HELP\tPrints this screen.");
			}
			else{
				System.out.println("FAIL");
			}
		}
		if( input.charAt(0) == '1' ) {
			if (input.contains("MOVE") ) {
				if( input.contains(" N") ) {
					move(1,0,-1);
				}
				else if( input.contains(" S") ) {
					move(1,0,1);
				}
				else if( input.contains(" W") ) {
					move(1,-1,0);
				}
				else if( input.contains(" E") ) {
					move(1,1,0);
				}
			}
			if( input.contains("LOOK") ) {
				look(1);
			}
		}
	}
	
	/**
	 * Determines whether the bot and the player are on the same square: if so, this function ends the game.
	 */
	protected void isPlayerKilled(){
		if( player.getCoord('x') == bot.getCoord('x') && player.getCoord('y') == bot.getCoord('y') ) {
			System.out.println( "You have been killed." );
			quitGame();
		}
		
	}

	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		System.out.print("Enter a full path for your map (including file extensions), or press enter for the default map >");
		BufferedReader mapNameStream = new BufferedReader(new InputStreamReader(System.in));
		boolean keepTryingMapName=true;
		
		try{
			while( keepTryingMapName ){
				String input = mapNameStream.readLine();
				if( input.length() == 0) {
					keepTryingMapName = false;
					logic.gameMap = new Map(1);
				}
				else {
					keepTryingMapName = false;
					logic.gameMap = new Map(input);
				}
			}
		}catch (IOException e){
			System.exit(1);
		}
		
		logic.player.setPosition(logic.gameMap.findSpawnPoint());
		logic.bot.setPosition(logic.gameMap.findSpawnPoint());
		
		while(logic.isGameRunning){
			logic.processInput( logic.bot.getNextAction() );
			logic.isPlayerKilled();
			logic.processInput( logic.player.getNextAction().toUpperCase() );
			logic.isPlayerKilled();
			System.out.println();	//adds a break between output of last command and input of current command.
		}

	}
}