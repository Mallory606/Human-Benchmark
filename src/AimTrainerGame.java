import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class AimTrainerGame extends MiniGame{
    private Label remainingLabel;
    private Canvas target;
    private int targetHits;
    private long startTime;

    public AimTrainerGame(){ super("Aim Trainer", false); }

    @Override
    public void initializeWindow(Stage primaryStage){
        setGameStage(new Stage());
        getGameStage().initModality(Modality.APPLICATION_MODAL);
        getGameStage().initOwner(primaryStage);
        getGameStage().setAlwaysOnTop(true);
        getGameStage().setTitle(getName());

        Label title = new Label("Aim Trainer");
        title.setFont(new Font(30));
        remainingLabel = new Label("Remaining: 30");
        remainingLabel.setFont(new Font(20));
        BorderPane labelBorder = new BorderPane();
        labelBorder.setCenter(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        labelBorder.setRight(remainingLabel);
        BorderPane.setAlignment(remainingLabel, Pos.CENTER);

        target = new Canvas(100, 100);
        drawTarget();
        target.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            targetHits++;
            if(targetHits == 30){
                long nanoElapsed = System.nanoTime()-startTime;
                long millisElapsed = TimeUnit.NANOSECONDS.toMillis(nanoElapsed);
                setCurrScore((int)(millisElapsed/30));
                gameOverPopUp();
            }
            else{ changeAnchors(); }
        });
        AnchorPane centerScreen = new AnchorPane(target);

        BorderPane border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.OLDLACE, new CornerRadii(10), new Insets(0))));
        border.setTop(labelBorder);
        BorderPane.setAlignment(title, Pos.CENTER);
        border.setCenter(centerScreen);
        BorderPane.setAlignment(centerScreen, Pos.CENTER);

        Scene scene = new Scene(border, 800, 545);
        getGameStage().setScene(scene);
        getGameStage().show();

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){ updateRemaining(); }
        };
        a.start();
        instructionsPopUp();
    }

    @Override
    public void playGame(){
        startTime = System.nanoTime();
        changeAnchors();
    }

    private void drawTarget(){
        GraphicsContext gc = target.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillOval(0, 0, 100, 100);
        gc.setFill(Color.OLDLACE);
        gc.fillOval(10, 10, 80, 80);
        gc.setFill(Color.RED);
        gc.fillOval(22, 22, 56, 56);
        gc.setFill(Color.OLDLACE);
        gc.fillOval(33, 33, 34, 34);
        gc.setFill(Color.RED);
        gc.fillOval(45, 45, 10, 10);
        AnchorPane.setTopAnchor(target, 400.0);
        AnchorPane.setLeftAnchor(target, 700.0);
    }

    private void changeAnchors(){
        double topRand = Math.random()*400;
        double leftRand = Math.random()*700;
        AnchorPane.setTopAnchor(target, topRand);
        AnchorPane.setLeftAnchor(target, leftRand);
    }

    private void updateRemaining(){
        remainingLabel.setText("Remaining: "+(30-targetHits));
    }

    @Override
    public void instructionsPopUp(){
        targetHits = 0;
        playGame();
    }
}
