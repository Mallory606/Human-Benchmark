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

/******************************************************************************
 * Ashley Krattiger                                                           *
 *                                                                            *
 * VisualMemoryGame                                                           *
 * This class runs through the Visual Memory Test from Human Benchmark. Play  *
 * by memorizing the positions of the highlighted squares and clicking the    *
 * squares to recreate those positions. Each round allows 3 mistakes before   *
 * you lose a life.                                                           *
 *****************************************************************************/
public class VisualMemoryGame extends MiniGame{
    /**************************************************************************
     * Global Variables:                                                      *
     * gameBoard - int[][] that holds ints for each square of the board that  *
     *             will be displayed on the window. Holds 1 if square is      *
     *             highlighted and 0 if it is not                             *
     * canvasBoard - Canvas[][] that holds the visual representation of values*
     *               in gameBoard                                             *
     * scoreLabel - Label that displays your current score                    *
     * livesLabel - Label that displays how many lives you have               *
     * visible - keeps track of whether the display should show which squares *
     *           are highlighted                                              *
     * gameRunning - boolean that keeps track of whether the window should    *
     *               allow displayed Nodes to change                          *
     * numSquares - int that holds the number of highlighted squares in this  *
     *              round                                                     *
     * numSelected - int that holds the number of correct squares the user has*
     *               clicked in the current round                             *
     * numLives - int that holds the number of lives the user has left        *
     * numStrikes - int that holds the number of incorrect squares the user   *
     *              has clicked in the current round                          *
     *************************************************************************/
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

    /**************************************************************************
     * Constructor - Calls super and provides this game's name                *
     *************************************************************************/
    public VisualMemoryGame(){ super("Visual Memory"); }

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
     * x - final version of the int i that is being iterated through for the  *
     *     purpose of making sure the Canvas eventHandlers know which one to  *
     *     change                                                             *
     * y - final version of the int j that is being iterated through for the  *
     *     purpose of making sure the Canvas eventHandlers know which one to  *
     *     change                                                             *
     * canvasBox - VBox that holds the grid of Canvases for the display       *
     * row - HBox that iterates through every HBox that is given to canvasBox *
     * title - Label that holds the title of the game                         *
     * labelPane - BorderPane that holds the Labels displayed at the top of   *
     *             the window                                                 *
     * border - BorderPane that organizes the Nodes for the gameplay window   *
     * scene - Scene for the gameplay window                                  *
     * a - AnimationTimer that updates the visuals of the gameplay window     *
     *************************************************************************/
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

    /**************************************************************************
     * playGame                                                               *
     *                                                                        *
     * Overridden from MiniGame class                                         *
     * Initializes visuals and variables for each round and starts the timer  *
     * Thread                                                                 *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *                                                                        *
     * Variables:                                                             *
     * timer - Thread that waits for 1.5 seconds and then stops the visible   *
     *         highlighted squares from being visible. Closes on its own once *
     *         it is done with resetting values for the round                 *
     * randI - int used for randomly generated value which picks an i from    *
     *         gameBoard to pick which square will be highlighted             *
     * randJ - int used for randomly generated value which picks a j from     *
     *         gameBoard to pick which square will be highlighted             *
     * numsPlaced - boolean that starts as true but changes to false if the   *
     *              same square is chosen to be highlighted twice             *
     *************************************************************************/
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

    /**************************************************************************
     * drawCanvases                                                           *
     *                                                                        *
     * Draws the squares on the window. Changes color to white for initial    *
     * showing of the highlighted squares                                     *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *                                                                        *
     * Variables:                                                             *
     * gc - GraphicsContext for each Canvas in canvasBoard (iterates)         *
     *************************************************************************/
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

    /**************************************************************************
     * updateLabels                                                           *
     *                                                                        *
     * Updates the text of global variables scoreLabel and livesLabel to show *
     * the most current information                                           *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void updateLabels(){
        scoreLabel.setText("Score: " + getCurrScore());
        livesLabel.setText("Lives: " + numLives);
    }

    /**************************************************************************
     * resetBoard                                                             *
     *                                                                        *
     * Sets the value of every square in gameBoard and canvasBoard to default *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void resetBoard(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                gameBoard[i][j] = 0;
                canvasBoard[i][j].getGraphicsContext2D().setFill(Color.ROSYBROWN);
            }
        }
    }

    /**************************************************************************
     * resetColors                                                            *
     *                                                                        *
     * Sets the color of every square to the default color                    *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void resetColors(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                canvasBoard[i][j].getGraphicsContext2D().setFill(Color.ROSYBROWN);
            }
        }
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
     * border - BorderPane for the instructions pop up window                 *
     * scene - Scene for the instructions pop up window                       *
     *************************************************************************/
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
