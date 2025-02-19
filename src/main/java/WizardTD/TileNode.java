package WizardTD;

/**
 * A class to be instantiated for each 
 * tile of the pathway in the map
 */
public class TileNode {
    public int col;
    public int row;
    public int cost;
    TileNode parent;
    
    /**
     * Creates an instance of the tile node
     * 
     * @param col Column of the tile node
     * @param row Row of the tile node
     * @param cost How many tiles from the start to the tile node
     * @param parent The tile node before this tile node in the pathway
     */
    TileNode(int col, int row, int cost, TileNode parent) {
        this.col = col;
        this.row = row;
        this.cost = cost;
        this.parent = parent;
    }
}
