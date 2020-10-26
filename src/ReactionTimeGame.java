import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReactionTimeGame extends MiniGame{
    private Canvas centerScreen;
    private boolean clickReady;
    private long startTime;

    public ReactionTimeGame(){ super("Reaction Time"); }

    @Override
    public void initializeWindow(Stage primaryStage){
        setGameStage(new Stage());
        getGameStage().initModality(Modality.APPLICATION_MODAL);
        getGameStage().initOwner(primaryStage);
        getGameStage().setAlwaysOnTop(true);
        getGameStage().setTitle(getName());

        centerScreen = new Canvas(600, 400);

        BorderPane border = new BorderPane();
        border.setCenter(centerScreen);
        BorderPane.setAlignment(centerScreen, Pos.CENTER);

        Scene scene = new Scene(border, 600, 400);
        getGameStage().setScene(scene);
        getGameStage().show();

        clickReady = false;

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
                drawCanvas();
            }
        };
        a.start();
        instructionsPopUp();
    }

    @Override
    public void playGame(){
        Thread timer = new Thread(() ->{
            Object o = new Object();
            int rand = (int)(Math.random()*5000)+3000;
            synchronized(o){
                try{
                    o.wait(rand);
                    clickReady = true;
                    startTime = System.nanoTime();
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void drawCanvas(){
        GraphicsContext gc = centerScreen.getGraphicsContext2D();
        gc.setFont(new Font(80));
        if(clickReady){
            gc.setFill(Color.LIME);
            gc.fillRect(0, 0, 600, 400);
            gc.setFill(Color.WHITE);
            gc.fillText("Click!", 200, 225);
        }
        else{
            gc.setFill(Color.RED);
            gc.fillRect(0, 0, 600, 400);
            gc.setFill(Color.WHITE);
            gc.fillText("Wait for green", 50, 225);
        }
    }

    @Override
    public void instructionsPopUp(){

    }
}
