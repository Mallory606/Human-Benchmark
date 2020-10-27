import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class ReactionTimeGame extends MiniGame{
    private Canvas centerScreen;
    private boolean gameRunning;
    private boolean clickReady;
    private long startTime;

    public ReactionTimeGame(){ super("Reaction Time", true); }

    @Override
    public void initializeWindow(Stage primaryStage){
        setGameStage(new Stage());
        getGameStage().initModality(Modality.APPLICATION_MODAL);
        getGameStage().initOwner(primaryStage);
        getGameStage().setAlwaysOnTop(true);
        getGameStage().setTitle(getName());

        centerScreen = new Canvas(600, 400);
        centerScreen.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            long elapsedNano = System.nanoTime()-startTime;
            long elapsedMillis;
            if(clickReady){
                elapsedMillis = TimeUnit.NANOSECONDS.toMillis(elapsedNano);
                setCurrScore((int)elapsedMillis);
                gameOverPopUp();
            }
            else{ instructionsPopUp(); }
        });

        BorderPane border = new BorderPane();
        border.setCenter(centerScreen);
        BorderPane.setAlignment(centerScreen, Pos.CENTER);

        Scene scene = new Scene(border, 600, 400);
        getGameStage().setScene(scene);
        getGameStage().show();

        clickReady = false;
        gameRunning = false;

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
                    if(gameRunning){
                        clickReady = true;
                        startTime = System.nanoTime();
                    }
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        clickReady = false;
        timer.start();
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
        Stage instructionsStage = new Stage();
        Label instructions = new Label("     This game will test your\n  "+
                "reaction time in milliseconds.\n Click the screen when it "+
                "turns \n                    green!");
        Button startButton = new Button("Start Game");
        BorderPane borderPane = new BorderPane();
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

        gameRunning = false;
        clickReady = false;

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 300, 200);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
