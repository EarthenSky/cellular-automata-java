import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import javafx.event.ActionEvent;

public class GridCanvas {
    
    public final static int CANVAS_SIZE = 640;      // in px
    public final static int DEFAULT_GRID_SIZE = 32; // in px
    
    private final Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
    private GraphicsContext gc;

    private boolean isCheckered = false;
    public int grid_size = DEFAULT_GRID_SIZE;


    public GridCanvas () {
        gc = canvas.getGraphicsContext2D();
        flushCanvas();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    // ignore parameter, lol
    public void flushCanvas(ActionEvent actionEvent) {
        flushCanvas();
    }

    public void flushCanvas() {
        gc.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
    }

    public void fillCanvas(Color col) {
        if (isCheckered) {
            System.out.println("TODO: this");
        } else {
            gc.setFill(col);
            gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
        }
    }
    

}