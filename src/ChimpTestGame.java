import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
                    if(gameRunning){
                        canvasBoard[x][y].getGraphicsContext2D()
                                .setStroke(Color.DEEPSKYBLUE);
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
        border.setTop(labelPane);
        border.setCenter(canvasBox);

        Scene scene = new Scene(border, 1055, 685);
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
        playGame();
    }

    @Override
    public void playGame(){
        instructionsPopUp();
        numNumbers = 4;
        numStrikes = 0;

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
                if(visible){
                    gc.setFont(new Font(50));
                    gc.strokeText("" + gameBoard[i][j], 30, 30);
                }
            }
        }
    }

    private void updateLabels(){
        scoreLabel.setText("Score: " + getCurrScore());
        strikeLabel.setText("Strikes: " + numStrikes);
    }

    private void instructionsPopUp(){
        Stage instructionsStage = new Stage();
        Label instructions = new Label("The screen will show the positions "+
                "of the numbers, then they will disappear after a few seconds."+
                " When this happens, press the squares with numbers in their "+
                "numerical order. If you miss one, you will get a strike. If"+
                "you get three strikes the game will end. Good luck!");
        Button startButton = new Button("Start Game");
        BorderPane border = new BorderPane();
        instructionsStage.initModality(Modality.APPLICATION_MODAL);
        instructionsStage.initOwner(chimpTestStage);
        instructionsStage.setAlwaysOnTop(true);
        instructionsStage.setTitle("Instructions");
        instructions.setFont(new Font(30));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            instructionsStage.close();
            gameRunning = true;
        });
        border.setCenter(instructions);
        border.setBottom(startButton);
    }
}
