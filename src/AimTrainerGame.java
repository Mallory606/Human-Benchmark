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

import java.util.concurrent.TimeUnit;

/******************************************************************************
 * Ashley Krattiger                                                           *
 *                                                                            *
 * AimTrainerGame                                                             *
 * This class runs through the Aim Trainer Test from Human Benchmark. Play by *
 * clicking the target on the screen 30 times.                                *
 *****************************************************************************/
public class AimTrainerGame extends MiniGame{
    /**************************************************************************
     * Global Variables:                                                      *
     * remainingLabel - Label that displays the number of targets you still   *
     *                  need to click                                         *
     * target - Canvas displaying the image of the target. Global so the      *
     *          position can change throughout the game                       *
     * targetHits - int that holds the number of times the target has been    *
     *              clicked so far                                            *
     * startTime - long that holds the nanosecond value of when the game      *
     *             started                                                    *
     *************************************************************************/
    private Label remainingLabel;
    private Canvas target;
    private int targetHits;
    private long startTime;

    /**************************************************************************
     * Constructor - Calls super and provides this game's name                *
     *************************************************************************/
    public AimTrainerGame(){ super("Aim Trainer", " ms", false); }

    /**************************************************************************
     * initializeWindow                                                       *
     *                                                                        *
     * Overridden from MiniGame class                                         *
     * Sets up gameplay window and initializes it and the AnimationTimer      *
     *                                                                        *
     * @param primaryStage - stage for the main menu window                   *
     * Returns nothing                                                        *
     *                                                                        *
     * Variables:                                                             *
     * title - Label that holds the name of the game                          *
     * labelBorder - BorderPane that holds the labels displayed at the top of *
     *               the window                                               *
     * centerScreen - AnchorPane that holds the target Canvas                 *
     * border - BorderPane for the gameplay window                            *
     * scene - Scene for the gameplay window                                  *
     * a - AnimationTimer that updates the visuals of the gameplay window and *
     *    handles game end logic to calculate words per minute                *
     *************************************************************************/
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
                long millisElapsed =TimeUnit.NANOSECONDS.toMillis(nanoElapsed);
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

    /**************************************************************************
     * playGame                                                               *
     *                                                                        *
     * Overridden from MiniGame class                                         *
     * Initializes position of target for each round                          *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    @Override
    public void playGame(){
        startTime = System.nanoTime();
        changeAnchors();
    }

    /**************************************************************************
     * drawTarget                                                             *
     *                                                                        *
     * Draws the target on the Canvas target                                  *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
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
    }

    /**************************************************************************
     * changeAnchors                                                          *
     *                                                                        *
     * Moves the anchors for global Canvas target to a random distance        *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void changeAnchors(){
        double topRand = Math.random()*400;
        double leftRand = Math.random()*700;
        AnchorPane.setTopAnchor(target, topRand);
        AnchorPane.setLeftAnchor(target, leftRand);
    }

    /**************************************************************************
     * updateRemaining                                                        *
     *                                                                        *
     * Updates the global remainingLabel with the most current information    *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void updateRemaining(){
        remainingLabel.setText("Remaining: "+(30-targetHits));
    }

    /**************************************************************************
     * instructionsPopUp                                                      *
     *                                                                        *
     * Overridden from MiniGame class                                         *
     * Initializes pop up window to display instructions for the game and     *
     * handles everything necessary for restarting the game                   *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *                                                                        *
     * Variables:                                                             *
     * instructionsStage - Stage for the instructions pop up window           *
     * instructions - Label that holds the instructions for the game. Has     *
     *                extra spaces in it for better spacing on the display    *
     * startButton - Button that starts the game and closes the instructions  *
     * borderPane - BorderPane for the instructions pop up window             *
     * scene - Scene for the instructions pop up window                       *
     *************************************************************************/
    @Override
    public void instructionsPopUp(){
        Stage instructionsStage = new Stage();
        Label instructions = new Label(" This game will test your average\n  "+
                " reaction time in milliseconds.\n      Click the target 30 "+
                "times!");
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
            playGame();
        });

        targetHits = 0;

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 300, 150);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
