import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import javafx.event.ActionEvent;

public class CanvasManager {
    
    public final static int CANVAS_SIZE = 640;      // in px
    public final static int DEFAULT_GRID_SIZE = 32; // not in px
    
    private final Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
    private GraphicsContext gc;

    private boolean isCheckered = false;
    public int gridSize = DEFAULT_GRID_SIZE;


    public CanvasManager () {
        gc = canvas.getGraphicsContext2D();
        flushCanvas();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    // ignores parameter, lol
    public void flushCanvas(ActionEvent actionEvent) {
        flushCanvas();
    }

    public void flushCanvas() {
        gc.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
    }

    /*
    public void fillCanvas(Color col) {
        if (isCheckered) {
            System.out.println("TODO: this");
        } else {
            gc.setFill(col);
            gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
        }
    }
    */
    
    public void renderGridToCanvas(Simulation sim) {
        int[][] grid = sim.getGrid();

        gc.setFill(Color.BLACK);

        int gridSizePx = CANVAS_SIZE / gridSize;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid.length; x++) {
                int val = grid[y][x];
                if(val == 1) {
                    gc.fillRect(x * gridSizePx, y * gridSizePx, gridSizePx, gridSizePx);
                }
            
            }
        }

    }


}