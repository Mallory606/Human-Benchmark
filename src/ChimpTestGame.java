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
    private Stage chimpTestStage;
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
        chimpTestStage = new Stage();
        chimpTestStage.initModality(Modality.APPLICATION_MODAL);
        chimpTestStage.initOwner(primaryStage);
        chimpTestStage.setAlwaysOnTop(true);
        chimpTestStage.setTitle(getName());

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
                    if(gameRunning && !visible){
                        if(gameBoard[x][y] == currNum){
                            canvasBoard[x][y].getGraphicsContext2D()
                                    .setStroke(Color.DEEPSKYBLUE);
                            if(currNum == numNumbers){
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
                            if(numStrikes == 2){ gameRunning = false; }
                            else{ numStrikes++; }
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
        chimpTestStage.setScene(scene);
        chimpTestStage.show();

        AnimationTimer a = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawCanvases();
                updateLabels();
            }
        };
        a.start();

        numNumbers = 4;
        numStrikes = 0;
        instructionsPopUp();
    }

    @Override
    public void playGame(){
        Thread timer = new Thread(() -> {
           Object o = new Object();
           synchronized(o){
               try{
                   o.wait(3000);
                   visible = false;
                   currNum = 1;
               } catch(InterruptedException e){
                   e.printStackTrace();
               }
           }
        });
        int randI, randJ;
        visible = true;
        for(int i = 1; i <= numNumbers; i++){
            randI = (int)(Math.random()*6);
            randJ = (int)(Math.random()*10);
            gameBoard[randI][randJ] = i;
        }
        timer.start();
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

    private void instructionsPopUp(){
        Stage instructionsStage = new Stage();
        Label instructions = new Label("The screen will show the positions "+
                "of the\n numbers, then they will disappear after a\n few seconds."+
                " When this happens, press the\n squares with numbers in their "+
                "numerical\n order. If you miss one, you will get a strike.\n If"+
                " you get three strikes the game will end.\n" +
                "                          Good luck!");
        Button startButton = new Button("Start Game");
        BorderPane border = new BorderPane();
        Scene scene;
        instructionsStage.initModality(Modality.APPLICATION_MODAL);
        instructionsStage.initOwner(chimpTestStage);
        instructionsStage.setAlwaysOnTop(true);
        instructionsStage.setTitle("Instructions");
        instructions.setFont(new Font(20));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            instructionsStage.close();
            gameRunning = true;
            playGame();
        });
        border.setCenter(instructions);
        border.setBottom(startButton);
        border.setAlignment(instructions, Pos.CENTER);
        border.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(border, 400, 300);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
