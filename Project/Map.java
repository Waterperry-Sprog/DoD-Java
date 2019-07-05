import java.io.*;

/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {

	/* Representation of the map */
	private char[][] map;
	
	/* Gold required for the human player to win */
	private int goldRequired;
	
	
	/*instantiated as "" rather than null so that the mapName can be processed by adding individual words
	 *as trying to add words to a null string results in "nullWhateverTextIsBeingAdded". 
	 */
	private String mapName = "";
	
	//variables used to determine the map size in the array.
	private int lineCounter;
	private int maxLineLength;
	
	
	/**
	 * Default constructor, creates the default map "Very small Labyrinth of doom".
	 */
	public Map(int a) {
		mapName = "Very small Labyrinth of doom";
		goldRequired = 2;
		map = new char[][]{
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
	}
	
	
	public Map(String mapName){
		goldRequired=0;
		BufferedReader mapReader;
		try{
			mapReader = new BufferedReader(new FileReader(mapName));
			System.out.println("Found mapfile.");
			String line = "";
			lineCounter = 0;
			maxLineLength = 0;
			String mapAsLine = "";
			while( ( line = mapReader.readLine() ) != null ){
				if( line.contains("win")){
					String[] winLine = line.split(" ");
					
					/*work out how much gold is required from the mapfile*/
					for(int i = 0; i < winLine[1].length(); i++){
						this.goldRequired *= 10;
						this.goldRequired += ( winLine[1].charAt(i) - '0' );
					}
				}
				
				/*work out the map name from the mapfile*/
				if( line.contains("name") ){
					String[] nameLine = line.split(" ");
					for(int i = 1; i < nameLine.length; i++){
						this.mapName = this.mapName + nameLine[i] + " ";
					}
					
					
				}
				if( !line.contains("win") && !line.contains("name") ){
					lineCounter++;
					maxLineLength = (line.length() > maxLineLength ? line.length() : maxLineLength);
					mapAsLine = mapAsLine + line;
				}
			}
			map= new char[maxLineLength][lineCounter];
			System.out.println("map Length: "+maxLineLength+"\tmap width: "+lineCounter);
			int n=0;
			for(int y = 0; y < lineCounter; y++){
				map[y] = mapAsLine.substring( n, n + maxLineLength ).toCharArray();
				n += maxLineLength;
			}
			
		}catch(IOException e){
			System.out.println("The file does not exist. Exiting.");
			System.exit(1);
		}catch(NullPointerException n){
		}
	}
	
	
    /**
     * @return Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return goldRequired;
    }
	
	/**
	 *
	 *This method returns the map name as a string.
	 *@return returns the map name as a String.
	 *
	 */
	protected String getMapName() {
		return mapName;
	}
	
	/**
	 *
	 *Method to get the tile at a certain point of the map. Try-catch structure necessary to stop an
	 *	ArrayIndexOutOfBoundsException when player uses look() near the edge of the map.
	 *@param x : the x co-ordinate of the map tile
	 *@param y : the y co-ordinate of the map tile
	 *@return returns the tile at (x,y).
	 *	 
	 */
	protected char getTile(int x, int y){
		try {
			return map[y][x];
		} catch(ArrayIndexOutOfBoundsException e) {
			return '\0';
		}
	}

	/**
	 *
	 *This method generates a random coordinate and tests its suitability for a spawn point.
	 *@return The coordinate of a suitable spawn point (not a G square or a wall.)
	 *
	 */
	protected int[] findSpawnPoint(){
		boolean keepSearching = true;
		int[] returnArray = {0,0};
		
		//maxLineLength and lineCounter are not evaluated for the default map, so the spawns need to be set differently.
		if( mapName == "Very small Labyrinth of doom" ) {
			try{
				while( keepSearching ){
					double randomX = Math.random()*20;
					double randomY = Math.random()*20;
					if( getTile( (int) randomX, (int) randomY ) == '.' || getTile( (int) randomX, (int) randomY ) == 'E') {
						returnArray[0] = (int) randomX;
						returnArray[1] = (int) randomY;
						keepSearching=false;
					}
				}
			}catch (NullPointerException n){
			}
			return returnArray;
		}
		
		//if default map is not desired, find a spawn point by the following method:
		else{
			try{
				while( keepSearching ){
					double randomX = Math.random() * this.maxLineLength;
					double randomY = Math.random() * this.lineCounter;
					if( getTile( (int) randomX, (int) randomY ) == '.' || getTile( (int) randomX, (int) randomY ) == 'E') {
						returnArray[0] = (int) randomX;
						returnArray[1] = (int) randomY;
						keepSearching=false;
					}
				}
			}catch (NullPointerException n){
			}
			return returnArray;
		}
			
		
	}

	/**
	 *
	 *Modifies the map in memory to replace tiles with another desired tile type.
	 *@param x : the x co-ordinate of the tile to be changed
	 *@param y : the y co-ordinate of the tile to be changed
	 *@param tile : the tile that the current tile at (x,y) should be changed to.
	 *
	 */
	protected void setTile(int x, int y, char tile) {
		map[y][x] = tile;
	}
}