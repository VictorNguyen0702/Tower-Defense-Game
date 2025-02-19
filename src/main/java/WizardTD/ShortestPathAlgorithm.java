package WizardTD;

import java.util.*;


/**
 * A class to find the shortest path between a start and end point
 * using Dijkstra's Algorithm
 * Source: https://stackoverflow.com/questions/12887921/shortest-path-in-a-2d-array-using-dijkstras-algorithm 
 */
public class ShortestPathAlgorithm {

    private char[][] layout;
    private ArrayList<ArrayList<int[]>> pathwayList;
    private ArrayList<int[]> startPoints;
    private int[] endPoint;

    /**
     * Creates an instance of the ShortestPathAlgorithm class
     * 
     * @param layout A char array that represents the map of the level
     */
    public ShortestPathAlgorithm(char[][] layout) {
        this.layout = layout;
        pathwayList = new ArrayList<>();
        findStarts();
        findEnd();
        for (int[] startPoint : startPoints) {
            ArrayList<int[]> pathway = findShortestPath(startPoint[0], startPoint[1], endPoint[0], endPoint[1]);
            pathwayList.add(pathway);
        }
    }

    /**
     * Uses the layout to find all the start points 
     * connected to the edge of the map
     */
    public void findStarts() {
        // finds the start points
        startPoints = new ArrayList<>();

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                if (layout[row][col] == 'X' && 
                (row == 0 || row == layout.length - 1 || col == 0 || col == layout[row].length - 1)) {
                    startPoints.add(new int[] {col, row});
                }
            }
        }
    }

    /**
     * Uses the layout to find the end point where the
     * wizard house is
     */
    public void findEnd() {
        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                if (layout[row][col] == 'W') {
                    endPoint = new int[] {col, row};
                }
            }
        }
    }

    /**
     * Finds the shortest path from a start point to an end point and also adds
     * in the spawn point outside the map
     * 
     * @param startCol The column of the starting point
     * @param startRow The row of the starting point
     * @param endCol The column of the end point
     * @param endRow The row of the end point
     * @return An arraylist with the tiles of the shortest path
     */
    public ArrayList<int[]> findShortestPath(int startCol, int startRow, int endCol, int endRow) {
        
        boolean[][] visited = new boolean[20][20];

        int[] spawnPoint;
        if (startRow == 0) { 
            spawnPoint = new int[] {startCol, startRow - 1};
        } else if (startRow == layout.length - 1) {
            spawnPoint = new int[] {startCol, startRow + 1};
        } else if (startCol == 0) {
            spawnPoint = new int[] {startCol - 1, startRow};
        } else // if(startCol == layout[startRow].length - 1) {
            spawnPoint = new int[] {startCol + 1, startRow};

        Comparator<TileNode> costComparator = Comparator.comparingInt(node -> node.cost);
        PriorityQueue<TileNode> queue = new PriorityQueue<>(costComparator);
        
        TileNode start = new TileNode(startCol, startRow, 0, null);
        queue.add(start);
        
        int[][] directions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // 4 different directions to move

        while (!queue.isEmpty()) {
            TileNode current = queue.poll();
            int col = current.col;
            int row = current.row;

            if (col == endCol && row == endRow) {
                ArrayList<int[]> path = new ArrayList<>();
                while (current != null) {
                    int[] currentCoords = {current.col, current.row};
                    path.add(currentCoords);
                    current = current.parent;
                }
                path.add(spawnPoint);
                Collections.reverse(path);
                return path;
            }

            visited[row][col] = true;

            for (int[] dir : directions) {
                int newCol = col + dir[0];
                int newRow = row + dir[1];

                if (validTile(newCol, newRow) && !visited[newRow][newCol] && isPath(newCol, newRow)) {
                    int newCost = current.cost + 1;
                    queue.add(new TileNode(newCol, newRow, newCost, current));
                }
            }
        }

        return null;
    }

    /**
     * Checks if a tile is within the ranges of the map
     * 
     * @param col The column of the tile
     * @param row The row of the tile
     * @return A boolean representing if it is within the boundaries or not
     */
    private boolean validTile(int col, int row) {
        return (row >= 0 && row < 20) && (col >= 0 && col < 20);
    }

    /**
     * Checks if a tile is a path or the wizard house
     * 
     * @param col The column of the tile
     * @param row The row of the tile
     * @return A boolean representing if it is a path or the wizard house
     */
    private boolean isPath(int col, int row) {
        return (layout[row][col] == 'X' || layout[row][col] == 'W');
    }

    /**
     * Retrieves a list of the pathways from all the start points to the end point
     * 
     * @return A list of the pathways from all the different start points to the end point
     */
    public ArrayList<ArrayList<int[]>> getPathwayList() {
        return this.pathwayList;
    }
}