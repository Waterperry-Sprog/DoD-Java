public class Bot extends HumanPlayer {
	
	/*how far the player is from the bot on the x axis*/
	private int playerLastSeenDeltaX;
	
	/*how far the player is from the bot on the y axis*/
	private int playerLastSeenDeltaY;
	
	/*how many moves the bot has made since its last look()*/
	private int movesSinceLook;
	
	/*the character passed to the previous move command.*/
	private char previousRandomMove;
	
	/**
	 * Default constructor for a Bot character.
	 */
	public Bot() {
		this.playerTile = 'B';
		playerLastSeenDeltaX = 0;
		playerLastSeenDeltaY = 0;
		movesSinceLook = 0;
	}
	
	/**
	 *
	 *Method to determine the next move of the bot. This method relies on the player's deltaX and deltaY from the bot.
	 *
	 *@return returns a string to be processed by GameLogic's processInput() method.
	 *
	 */
	protected String getNextAction() {
		String returnString = "1 ";
		
		//if the bot is not at the last sighted location of the player, it should move towards that location.
		if( playerLastSeenDeltaX != 0 || playerLastSeenDeltaY != 0 ){
			returnString += processMove();
		}
		
		//if the bot has not sighted the player yet, it should look for the player after each random move.
		else if( movesSinceLook > 0 ){
			returnString += "LOOK";
		}
		
		//otherwise it should just randomly move.
		else{
			returnString += generateRandomMove();
		}
		
		return returnString;
	}

	/**
	 * 
	 * Method to determine the bot's next move based on the last sighting of the player.
	 * 
	 * @return returns a MOVE string command for the bot to run.
	 */
	protected String processMove(){
		String moveString = "MOVE ";
		double xOrYAxis = Math.random();
		
		//if the player was last seen directly N/S/E/W of the player, then move towards them along the common axis.
		if( playerLastSeenDeltaX == 0 ){
			if( playerLastSeenDeltaY > 0 ){
				moveString += "N";
				playerLastSeenDeltaY -= 1;
			}
			else if( playerLastSeenDeltaY < 0 ){
				moveString += "S";
				playerLastSeenDeltaY += 1;
			}
		}
		else if( playerLastSeenDeltaY == 0 ){
			if( playerLastSeenDeltaX > 0 ){
				moveString += "E";
				playerLastSeenDeltaX -= 1;
			}
			else if( playerLastSeenDeltaX < 0 ){
				moveString += "W";
				playerLastSeenDeltaX += 1;
			}
		}
		
		//otherwise leave it to chance
		else{
			if( xOrYAxis > 0.5 ){
				if( playerLastSeenDeltaX > 0 ){
					moveString += "E";
					playerLastSeenDeltaX -= 1;
				}
				else if( playerLastSeenDeltaX < 0){
					moveString += "W";
					playerLastSeenDeltaX += 1;
				}
			}
			else {
				if( playerLastSeenDeltaY > 0 ){
					moveString += "N";
					playerLastSeenDeltaY -= 1;
				}
				else if( playerLastSeenDeltaY < 0 ){
					moveString += "S";
					playerLastSeenDeltaY += 1;
				}
			}
		}
		return moveString;
	}

	/**
	 * 
	 * This method processes the bot's look(), determining if the player is nearby and if so, where the player is.
	 * 
	 * @param look
	 * 		A string produced by GameLogic's look(1) containing the tiles immediately surrounding the bot.
	 */
	protected void processLook(String look){
		movesSinceLook = 0;
		for(int i = 0; i < look.length(); i++){
			if( look.charAt(i) == 'P' ){
				System.out.println("The bot has seen you!");
				
				//set the deltaX and deltaY according to the array position (these following lines map the LOOK string onto a 5x5 grid from -2-->2). 
				playerLastSeenDeltaX = i%5 - 2;
				playerLastSeenDeltaY = 2 - i/5;
				return;
			}
		}
		return;
	}
	
	/**
	 * 
	 * This method generates a random move for the bot. The method will not allow the bot to revert its previous move, which prevents
	 * 		the bot from just bouncing back and forth between two squares (an improbable but undesirable event). It also prevents the
	 * 		bot from performing the same move twice, preventing the bot continually bumping into a wall.
	 * 
	 * @return returns a randomly generated MOVE string.
	 */
	protected String generateRandomMove(){
		movesSinceLook++;
		double probability = Math.random();
		
		if(probability<=0.25){
			if( previousRandomMove != 'S' && previousRandomMove != 'N' ) {
				return "MOVE N";
			}
			else{
				return "MOVE W";
			}
		}
		else if( probability >= 0.75 ){
			if( previousRandomMove != 'E' && previousRandomMove != 'W' ) {
				return "MOVE W";
			}
			else{
				return "MOVE S";
			}
		}
		else if( probability<=0.5 ) {
			if( previousRandomMove != 'W' && previousRandomMove != 'E' ){
				return "MOVE E";
			}
			else{
				return "MOVE N";
			}
		}
		else{
			if( previousRandomMove != 'N' && previousRandomMove != 'S' ){
				return "MOVE S";
			}
			else{
				return "MOVE E";
			}
		}
	}
}