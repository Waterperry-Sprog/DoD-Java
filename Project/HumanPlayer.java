/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */
 
import java.io.*;

public class HumanPlayer {

	private char occupiedTile;
	private int xPosition;
	private int yPosition;
	private int goldOwned;
	protected char playerTile;
	
	/**
	 * Default constructor for HumanPlayer
	 */
    public HumanPlayer() {
		goldOwned = 0;
		playerTile = 'P';
	}
		
	/**
	 *
	 *Getter for occupiedTile
	 *@return returns the currently occupied tile as a char
	 *
	 */
	protected char getTile(){
		return occupiedTile;
	}
	
	/**
	 *
	 *Method to return the player's tile (P for human, B for bot). Used in printing map in look().
	 *@return the playerTile char defined in the constructor.
	 *
	 */
	protected char getPlayerTile(){
		return playerTile;
	}
	
	/**
	 *Setter for occupiedTile
	 *@param tile : char input to set as occupiedTile
	 *
	 */
	protected void setTile(char tile){
		occupiedTile = tile;
	}
	
	/**
	 *
	 *Sets the player position on the map as an x,y coordinate.
	 *@param pos : an int array consisting of two elements, where the first int is the x coordinate and the
	 *			   second is the y coordinate.
	 *
	 */
	protected void setPosition(int[] pos){
		xPosition = pos[0];
		yPosition = pos[1];
	}
	

	/**
	 *
	 *Getter for goldOwned
	 *@return returns the goldOwned as an int
	 *
	 */
	protected int getGoldOwned(){
		return goldOwned;
	}
	
	/**
	 *
	 *Method to add gold to player
	 *@param x : amount of gold to add
	 *
	 */
	protected void incGold(int x){
		goldOwned+=x;
	}

    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     * @return Processed output or Invalid if the @param command is wrong.
     */
    protected String getNextAction() {
		System.out.print("It's your turn. Enter command here >");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			while(true) {
				String input = in.readLine();
				if( input == null ) {
					System.exit(0);
				}
				return "0" + input;
			} 
		}catch(IOException e) {
				System.err.println( e.getMessage() );
				System.exit(1);
		}
        return null;
    }
	
	/**
	 *
	 *Gets the current coordinate (either x or y) in the array of the player
	 *@param coord : either x or y, whichever axis the desired coord lies on
	 *@return returns an int for the specified axis (x or y)
	 *
	 */
	int getCoord(char coord) {
		if(coord=='x') return this.xPosition;
		else return this.yPosition;
	}
}
