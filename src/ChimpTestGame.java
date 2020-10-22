import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    private int[][] gameBoard;
    private Canvas[][] canvasBoard;
    private Label scoreLabel;
    private boolean visible;

    public ChimpTestGame(){ super("Chimp Test"); }

    @Override
    public void initializeWindow(Stage primaryStage){
        Stage chimpTestStage = new Stage();
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
                    canvasBoard[x][y].getGraphicsContext2D()
                            .setStroke(Color.DEEPSKYBLUE);
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
        BorderPane labelPane = new BorderPane();
        labelPane.setCenter(title);
        labelPane.setRight(scoreLabel);

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
            }
        };
        a.start();
    }

    @Override
    public void playGame(){

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
}
