//import javafx.event.ActionEvent;
//import javafx.beans.value.ObservableValue;

import java.util.Random;

/**
 * This class handles the running portion of the cellular automa simulation.
 * 
 */
public class Simulation {
    
    // todo: currently each node in the grid can contain only 1 integer, not more complex values.
    private int[][] grid = new int[CanvasManager.DEFAULT_GRID_SIZE][CanvasManager.DEFAULT_GRID_SIZE];

    public float randomNoiseLimit = 0.5f;

    public void updateGrid(int size) {
        grid = new int[size][size];
    }

    // todo: return iterable.
    public int[][] getGrid() {
        return grid;
    }

    // randomly generates numbers between 0.0 and 1.0 and becomes 1 if > limit and 0 if < limit.
    public void applyBinaryRandomNoise() {
        Random random = new Random();
        
        // apply randomness to tiles.
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid.length; x++) {
                grid[y][x] = random.nextFloat() > randomNoiseLimit ? 1 : 0;
            }
        }
    }


    // ********************************************************************** //

    

}