import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
                                        if(numSquares == 20){
                                            gameRunning = false;
                                            gameOverPopUp();
                                        }
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
                                        if(numLives == 1){
                                            gameRunning = false;
                                            gameOverPopUp();
                                        }
                                        else{
                                            numLives--;
                                            playGame();
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
                Color.PINK, new CornerRadii(10), new Insets(0))));
        border.setTop(labelPane);
        border.setCenter(canvasBox);
        BorderPane.setAlignment(canvasBox, Pos.CENTER);

        Scene scene = new Scene(border, 525, 585);
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
        Thread timer = new Thread(() -> {
            Object o = new Object();
            synchronized(o){
                try{
                    o.wait(1500);
                    visible = false;
                    resetColors();
                    numSelected = 0;
                    numStrikes = 0;
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        int randI, randJ;
        boolean numsPlaced = false;
        visible = true;
        while(!numsPlaced){
            resetBoard();
            numsPlaced = true;
            for(int i = 0; i < numSquares; i++){
                randI = (int)(Math.random()*5);
                randJ = (int)(Math.random()*5);
                if(gameBoard[randI][randJ] != 0){ numsPlaced = false; }
                gameBoard[randI][randJ] = 1;
            }
        }
        timer.start();
    }

    private void drawCanvases(){
        GraphicsContext gc;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                gc = canvasBoard[i][j].getGraphicsContext2D();
                if(visible && gameBoard[i][j] == 1){ gc.setFill(Color.WHITE); }
                gc.fillRoundRect(0, 0, 100, 100, 5, 5);
            }
        }
    }

    private void updateLabels(){
        scoreLabel.setText("Score: " + getCurrScore());
        livesLabel.setText("Lives: " + numLives);
    }

    private void resetBoard(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                gameBoard[i][j] = 0;
                canvasBoard[i][j].getGraphicsContext2D().setFill(Color.ROSYBROWN);
            }
        }
    }

    private void resetColors(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                canvasBoard[i][j].getGraphicsContext2D().setFill(Color.ROSYBROWN);
            }
        }
    }

    @Override
    public void instructionsPopUp(){
        Stage instructionsStage = new Stage();
        Label instructions = new Label("      Every level, a number of tiles"+
                " will flash white.\n    Memorize them, and pick them again "+
                "after the\n                              tiles are reset!\n"+
                "\nLevels get progressively more difficult, to challenge\n   "+
                "                              your skills.\n\n     If you "+
                "miss 3 tiles on a level, you lose one life.\n\n             "+
                "               You have 3 lives.\n\n                     "+
                "Make it as far as you can!");
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
        numLives = 3;
        numStrikes = 0;
        setCurrScore(0);
        resetBoard();

        border.setCenter(instructions);
        border.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(border, 500, 400);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
