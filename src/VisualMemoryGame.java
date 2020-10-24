import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VisualMemoryGame extends MiniGame{
    private int[][] gameBoard;
    private Canvas[][] canvasBoard;
    private Label scoreLabel;
    private Label strikeLabel;
    private boolean visible;
    private boolean gameRunning;
    private int numSquares;
    private int numLives;
    private int currNum;

    public VisualMemoryGame(){ super("Visual Memory"); }

    @Override
    public void playGame(){

    }

    @Override
    public void initializeWindow(Stage primaryStage){

    }

    @Override
    public void instructionsPopUp(){
        Stage instructionsStage = new Stage();
        Label instructions = new Label("Every level, a number of tiles will"+
                "flash\n white. Memorize them, and pick them again\nafter the "+
                "tiles are reset!\n\nLevels get progressively more difficult, "+
                "to challenge\nyour skills.\n\nIf you miss 3 tiles on a level,\nyou"+
                " lose one life.\n\nYou have 3 lives.\n\nMake it as far as you can!");
        Button startButton = new Button("Start Game");
        BorderPane border = new BorderPane();
        Scene scene;
        instructionsStage.initModality(Modality.APPLICATION_MODAL);
        instructionsStage.initOwner(getGameStage());
        instructionsStage.setAlwaysOnTop(true);
        instructionsStage.setTitle("Instructions");
        instructions.setFont(new Font(20));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            instructionsStage.close();
            gameRunning = true;
            playGame();
        });

        numSquares = 3;
        numLives = 0;
        setCurrScore(0);
        resetBoard();

        border.setCenter(instructions);
        border.setBottom(startButton);
        border.setAlignment(instructions, Pos.CENTER);
        border.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(border, 400, 300);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
