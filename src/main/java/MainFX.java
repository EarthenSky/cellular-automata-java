import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Group;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


// launch class
public class MainFX extends Application {

    public final static int CANVAS_SIZE = 640;      // in px
    public final static int WINDOW_WIDTH = 1280;    // in px
    public final static int GAP = 16;               // in px

    public final static int DEFAULT_GRID_SIZE = 32;      // in px

    private int grid_size = DEFAULT_GRID_SIZE;

    // this guy starts the everything
    public static void main(String[] args) {
        Application.launch();  // no resize?
    }

    //public Label gridSizeLabel;
    private Menu gridSizeMenu;

    // this one does the heavy lifting
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cellular Automa Sim");

        // ****************************************************************** //

        // 2d graphics view

        final Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        StackPane leftArea = new StackPane(canvas);
        leftArea.setAlignment(Pos.CENTER);

        // ****************************************************************** //

        // menu bar stuff
        Button doubleButton = new Button("Double | *2");
        Button halveButton = new Button("Halve | /2");
        doubleButton.setOnAction( this::handleDoubleButton );
        halveButton.setOnAction( this::handleHalveButton );

        CustomMenuItem buttonGridSize = new CustomMenuItem();
        buttonGridSize.setHideOnClick(false);
        buttonGridSize.setContent( new HBox(doubleButton, halveButton) );

        gridSizeMenu = new Menu("Grid Size = " + grid_size);
        gridSizeMenu.getItems().add(buttonGridSize);

        Menu settings = new Menu("Settings");
        settings.getItems().addAll(gridSizeMenu);

        MenuItem refreshGrid = new MenuItem("Refresh Grid");

        Menu gridOptions = new Menu("Grid Options");
        gridOptions.getItems().addAll(refreshGrid);
        
        MenuBar menuBar = new MenuBar(settings, gridOptions);

        // ****************************************************************** //

        // just your basic description label
        Label editorLabel = new Label("Automa-script Editor: ");
        String defaultProgram = "var -> tmp \nparam size -> 3 \ncell has -> number \n\n//more code here \n";
        TextArea editor = new TextArea(defaultProgram);
        
        // Horizontally attached
        Button buttonSave = new Button("Save To File");
        Button buttonLoad = new Button("Load From File");
        HBox saveLoadMenu = new HBox(GAP / 2);
        saveLoadMenu.getChildren().addAll(buttonSave, buttonLoad);
        saveLoadMenu.setAlignment(Pos.TOP_LEFT);

        VBox rightBox = new VBox(GAP / 2);
        rightBox.getChildren().addAll(menuBar, editorLabel, editor, saveLoadMenu);  // add things to this panel
        rightBox.setAlignment(Pos.TOP_LEFT);
        //String style = "-fx-background-color: rgba(255, 70, 230, 0.6);";
        //rightBox.setStyle(style);

        // ****************************************************************** //

        // Do macro scale formatting
        GridPane mainBox = new GridPane();
        mainBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
        mainBox.setHgap(GAP);
        mainBox.add(leftArea, 0, 0, 1, 1);
        mainBox.add(rightBox, 1, 0, 1, 1);
        
        Scene scene = new Scene(mainBox, WINDOW_WIDTH, CANVAS_SIZE + GAP * 2, Color.WHITE);
        stage.setScene(scene);

        stage.show();
    }


    private void handleDoubleButton(ActionEvent actionEvent) {
        grid_size = grid_size * 2;
        gridSizeMenu.setText("Set Grid Size = " + grid_size);
    }

    private void handleHalveButton(ActionEvent actionEvent) {
        grid_size = grid_size / 2;
        if(grid_size == 0) {
            grid_size = 1;
        }

        gridSizeMenu.setText("Set Grid Size = " + grid_size);
    }


    // function to do canvas updates

}