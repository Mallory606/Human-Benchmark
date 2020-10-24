import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VisualMemoryGame extends MiniGame{
    private int[][] gameBoard;
    private Canvas[][] canvasBoard;
    private Label scoreLabel;
    private Label livesLabel;
    private boolean visible;
    private boolean gameRunning;
    private int numSquares;
    private int numSelected;
    private int numLives;
    private int numStrikes;

    public VisualMemoryGame(){ super("Visual Memory"); }

    @Override
    public void initializeWindow(Stage primaryStage){
        setGameStage(new Stage());
        getGameStage().initModality(Modality.APPLICATION_MODAL);
        getGameStage().initOwner(primaryStage);
        getGameStage().setAlwaysOnTop(true);
        getGameStage().setTitle(getName());

        gameBoard = new int[5][5];
        canvasBoard = new Canvas[5][5];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                final int x = i;
                final int y = j;
                gameBoard[i][j] = 0;
                canvasBoard[i][j] = new Canvas(100, 100);
                canvasBoard[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED,
                        event -> {
                            if(gameRunning && !visible){
                                if(gameBoard[x][y] == 1){
                                    numSelected++;
                                    canvasBoard[x][y].getGraphicsContext2D()
                                            .setFill(Color.WHITE);
                                    if(numSelected == numSquares){
                                        numSquares++;
                                        setCurrScore(getCurrScore()+1);
                                        resetBoard();
                                        playGame();
                                    }
                                }
                                else{
                                    canvasBoard[x][y].getGraphicsContext2D()
                                            .setFill(Color.RED);
                                    if(numStrikes == 2){
                                        if(numLives == 1) {
                                            gameRunning = false;
                                            gameOverPopUp();
                                        }
                                        else{
                                            numLives--;
                                        }
                                    }
                                    else{ numStrikes++; }
                                }
                            }
                        });
            }
        }
        visible = false;
        VBox canvasBox = new VBox(5);
        HBox row;
        for(int i = 0; i < 5; i++){
            row = new HBox(5);
            for(int j = 0; j < 5; j++){
                row.getChildren().add(canvasBoard[i][j]);
            }
            canvasBox.getChildren().add(row);
        }

        Label title = new Label("Visual Memory");
        title.setFont(new Font(40));
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font(30));
        livesLabel = new Label("Lives: 0");
        livesLabel.setFont(new Font(30));
        BorderPane labelPane = new BorderPane();
        labelPane.setCenter(title);
        labelPane.setRight(scoreLabel);
        labelPane.setLeft(livesLabel);

        BorderPane border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE, new CornerRadii(10), new Insets(0))));
        border.setTop(labelPane);
        border.setCenter(canvasBox);
        border.setAlignment(canvasBox, Pos.CENTER);

        Scene scene = new Scene(border, 1045, 685);
        getGameStage().setScene(scene);
        getGameStage().show();

        AnimationTimer a = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawCanvases();
                updateLabels();
            }
        };
        a.start();

        instructionsPopUp();
    }

    @Override
    public void playGame(){

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
        numSelected = 0;
        numLives = 0;
        numStrikes = 0;
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
