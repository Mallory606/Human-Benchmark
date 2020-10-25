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

public class ChimpTestGame extends MiniGame{
    private int[][] gameBoard;
    private Canvas[][] canvasBoard;
    private Label scoreLabel;
    private Label strikeLabel;
    private boolean visible;
    private boolean gameRunning;
    private int numNumbers;
    private int numStrikes;
    private int currNum;

    public ChimpTestGame(){ super("Chimp Test"); }

    @Override
    public void initializeWindow(Stage primaryStage){
        setGameStage(new Stage());
        getGameStage().initModality(Modality.APPLICATION_MODAL);
        getGameStage().initOwner(primaryStage);
        getGameStage().setAlwaysOnTop(true);
        getGameStage().setTitle(getName());

        gameBoard = new int[6][10];
        canvasBoard = new Canvas[6][10];
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 10; j++){
                final int x = i;
                final int y = j;
                gameBoard[i][j] = 0;
                canvasBoard[i][j] = new Canvas(100, 100);
                canvasBoard[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED,
                        event -> {
                    if(gameRunning){
                        if(visible){ visible = false; }
                        if(gameBoard[x][y] == currNum){
                            canvasBoard[x][y].getGraphicsContext2D()
                                    .setStroke(Color.DEEPSKYBLUE);
                            if(currNum == numNumbers){
                                if(numNumbers == 30){
                                    gameRunning = false;
                                    gameOverPopUp();
                                }
                                numNumbers++;
                                setCurrScore(getCurrScore()+1);
                                resetBoard();
                                playGame();
                            }
                            else{ currNum++; }
                        }
                        else{
                            canvasBoard[x][y].getGraphicsContext2D()
                                    .setStroke(Color.RED);
                            if(numStrikes == 2){
                                gameRunning = false;
                                gameOverPopUp();
                            }
                            else{
                                numStrikes++;
                                playGame();
                            }
                        }
                    }
                });
            }
        }
        visible = false;
        VBox canvasBox = new VBox(5);
        HBox row;
        for(int i = 0; i < 6; i++){
            row = new HBox(5);
            for(int j = 0; j < 10; j++){
                row.getChildren().add(canvasBoard[i][j]);
            }
            canvasBox.getChildren().add(row);
        }

        Label title = new Label("Chimp Test");
        title.setFont(new Font(40));
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font(30));
        strikeLabel = new Label("Strikes: 0");
        strikeLabel.setFont(new Font(30));
        BorderPane labelPane = new BorderPane();
        labelPane.setCenter(title);
        labelPane.setRight(scoreLabel);
        labelPane.setLeft(strikeLabel);

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
        int randI, randJ;
        boolean numsPlaced = false;
        visible = true;
        currNum = 1;
        while(!numsPlaced){
            resetBoard();
            numsPlaced = true;
            for(int i = 1; i <= numNumbers; i++){
                randI = (int)(Math.random()*6);
                randJ = (int)(Math.random()*10);
                //System.out.println("i = " + randI + "     j = " + randJ);
                if(gameBoard[randI][randJ] != 0){ numsPlaced = false; }
                gameBoard[randI][randJ] = i;
            }
        }
    }

    private void drawCanvases(){
        GraphicsContext gc;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 10; j++){
                gc = canvasBoard[i][j].getGraphicsContext2D();
                gc.setLineWidth(7);
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, 100, 100);
                gc.strokeRect(0, 0, 100, 100);
                if(visible && gameBoard[i][j] != 0){
                    gc.setFont(new Font(30));
                    gc.setLineWidth(2);
                    gc.strokeText("" + gameBoard[i][j], 40, 60);
                }
            }
        }
    }

    private void updateLabels(){
        scoreLabel.setText("Score: " + getCurrScore());
        strikeLabel.setText("Strikes: " + numStrikes);
    }

    private void resetBoard(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 10; j++){
                gameBoard[i][j] = 0;
                canvasBoard[i][j].getGraphicsContext2D().setStroke(Color.BLACK);
            }
        }
    }

    @Override
    public void instructionsPopUp(){
        Stage instructionsStage = new Stage();
        Label instructions = new Label("The screen will show the positions "+
                "of several\n   numbers, which will disappear after you \n   "+
                "press one. You must press the squares in\n  numerical order. "+
                " If you miss one, you will \n    get a strike. If"+
                " you get three strikes the \n                        game "+
                "will end.\n                           Good luck!");
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

        numNumbers = 4;
        numStrikes = 0;
        setCurrScore(0);
        resetBoard();

        border.setCenter(instructions);
        border.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(border, 400, 300);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
