import javafx.application.Application;
import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


// launch class
public class MainFX extends Application {
    
    public final static int WINDOW_WIDTH = 1280;    // in px
    public final static int GAP = 16;               // in px

    private Menu gridSizeMenu;  // todo: make this not a variable

    private CanvasManager canvasManager = new CanvasManager();
    private Simulation simulation = new Simulation();

    private String program = "";

    // this guy starts the everything
    public static void main(String[] args) {
        Application.launch();  // no resize?
    }

    // this one does the heavy lifting
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cellular Automata!");

        // ****************************************************************** //

        // 2d graphics view

        StackPane leftArea = new StackPane( canvasManager.getCanvas() );
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

        gridSizeMenu = new Menu("Grid Size = " + canvasManager.gridSize);
        gridSizeMenu.getItems().add(buttonGridSize);

        Menu settings = new Menu("Settings");
        settings.getItems().addAll(gridSizeMenu);

        MenuItem refreshGrid = new MenuItem("Refresh Grid");
        refreshGrid.setOnAction( canvasManager::flushCanvas );

        Menu gridOptions = new Menu("Grid Options");
        gridOptions.getItems().addAll(refreshGrid);

        TextField randomNoiseLimitField = new TextField( Float.toString(simulation.randomNoiseLimit) );
        randomNoiseLimitField.textProperty().addListener( (observable, oldValue, newValue) -> {
                try {
                    simulation.randomNoiseLimit = Float.valueOf( newValue );
                } catch(NumberFormatException nfe) {
                    randomNoiseLimitField.setText(oldValue);
                }
            } );

        CustomMenuItem randomNoiseLimit = new CustomMenuItem();
        randomNoiseLimit.setHideOnClick(false);
        randomNoiseLimit.setContent(randomNoiseLimitField);

        Button randomNoiseGenerateButton = new Button( "Generate" );
        randomNoiseGenerateButton.setOnAction( (actionEvent) -> {
                canvasManager.flushCanvas();
                simulation.applyBinaryRandomNoise();
                canvasManager.renderGridToCanvas(simulation);
            } );

        CustomMenuItem randomNoiseGenerate = new CustomMenuItem();
        randomNoiseGenerate.setHideOnClick(false);
        randomNoiseGenerate.setContent(randomNoiseGenerateButton);

        Menu randomNoiseMenu = new Menu("Random Noise");
        randomNoiseMenu.getItems().addAll( randomNoiseLimit, randomNoiseGenerate  );

        Menu generate = new Menu("Generate");
        generate.getItems().addAll(randomNoiseMenu);
        
        MenuBar menuBar = new MenuBar(settings, gridOptions, generate);

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
        
        Scene scene = new Scene(mainBox, WINDOW_WIDTH, CanvasManager.CANVAS_SIZE + GAP * 2, Color.WHITE);
        stage.setScene(scene);

        stage.show();
    }


    private void handleDoubleButton(ActionEvent actionEvent) {
        canvasManager.gridSize *= 2;

        gridSizeMenu.setText("Grid Size = " + canvasManager.gridSize);
        simulation.updateGrid(canvasManager.gridSize);
    }

    private void handleHalveButton(ActionEvent actionEvent) {
        canvasManager.gridSize /= 2;
        if(canvasManager.gridSize == 0) {
            canvasManager.gridSize = 1;
        }

        gridSizeMenu.setText("Grid Size = " + canvasManager.gridSize);
        simulation.updateGrid(canvasManager.gridSize);
    }

}