import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/******************************************************************************
 * Ashley Krattiger                                                           *
 *                                                                            *
 * NumberMemoryGame                                                           *
 * This class runs through the Number Memory Test from Human Benchmark. Play  *
 * by typing the given number in the text field on the bottom of the window   *
 * once the number disappears. Press "Submit" to check your answer. Pressing  *
 * the "Next" button from the checking screen will either let you move on to  *
 * the next round or open up the Game Over pop up.                            *
 *****************************************************************************/
public class NumberMemoryGame extends MiniGame{
    /**************************************************************************
     * Global Variables:                                                      *
     * levelLabel - Label that displays the level you are currently on        *
     * centerScreen - Canvas that draws the numbers and information on the    *
     *                screen                                                  *
     * textField - TextField that reads user input for repeating the given    *
     *             number                                                     *
     * userPanel - HBox that holds the user interface for inputting a number. *
     *             Global so it can be added and removed from the BorderPane  *
     *             as needed                                                  *
     * nextButton - Button that lets user move on from the checking answer    *
     *              screen. Global so it can be added and removed from the    *
     *              BorderPane as needed                                      *
     * border - BorderPane for the game play window. Global so nodes can be   *
     *          added and removed as needed                                   *
     * number - int representing the number that is to be memorized and       *
     *          repeated by the user                                          *
     * numDigits - int that holds the number of digits in number. Doubles as  *
     *             the number for the level the player is on                  *
     * input - int value that was input by the user                           *
     * visible - boolean that keeps track of whether the given number is shown*
     *           on the screen                                                *
     * roundEnd - boolean that keeps track of whether the checking numbers    *
     *            screen should be on the screen                              *
     * setUserPanel - boolean that keeps track of whether the userPanel needs *
     *                to be re-added to the screen                            *
     *************************************************************************/
    private Label levelLabel;
    private Canvas centerScreen;
    private TextField textField;
    private HBox userPanel;
    private Button nextButton;
    private BorderPane border;
    private int number;
    private int numDigits;
    private int input;
    private boolean visible;
    private boolean roundEnd;
    private boolean setUserPanel;

    /**************************************************************************
     * Constructor - Calls super and provides this game's name                *
     *************************************************************************/
    public NumberMemoryGame(){ super("Number Memory", " rounds", false); }

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
     * title - Label that holds the title of the game                         *
     * labelPane - BorderPane that holds the Labels shown on the top of the   *
     *             screen                                                     *
     * submit - Button that submits the user input for comparison to the given*
     *          number                                                        *
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

        Label title = new Label("Number Memory");
        title.setFont(new Font(30));
        levelLabel = new Label("Level 1");
        levelLabel.setFont(new Font(20));
        BorderPane labelPane = new BorderPane();
        labelPane.setCenter(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        labelPane.setRight(levelLabel);
        BorderPane.setAlignment(levelLabel, Pos.CENTER);

        centerScreen = new Canvas(600, 250);

        textField = new TextField("");
        textField.setFont(new Font(20));
        Button submit = new Button("Submit");
        submit.setFont(new Font(20));
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            input = getInput();
            roundEnd = true;
            border.setBottom(nextButton);
        });
        userPanel = new HBox(10);
        userPanel.getChildren().addAll(textField, submit);
        userPanel.setAlignment(Pos.CENTER);

        nextButton = new Button("Next");
        nextButton.setFont(new Font(20));
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean allSame = true;
            for(int i : compareNumbers()){
                if(i == 0){
                    allSame = false;
                    break;
                }
            }
            if(allSame){
                if(numDigits == 20){
                    setCurrScore(numDigits);
                    gameOverPopUp();
                }
                else{
                    numDigits++;
                    visible = true;
                    roundEnd = false;
                    border.setBottom(null);
                    playGame();
                }
            }
            else{
                setCurrScore(numDigits);
                gameOverPopUp();
            }
        });

        border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.THISTLE, new CornerRadii(10), new Insets(0))));
        border.setTop(labelPane);
        BorderPane.setAlignment(labelPane, Pos.CENTER);
        border.setCenter(centerScreen);
        BorderPane.setAlignment(centerScreen, Pos.CENTER);
        BorderPane.setAlignment(userPanel, Pos.CENTER);
        BorderPane.setAlignment(nextButton, Pos.CENTER);

        Scene scene = new Scene(border, 600, 400);
        getGameStage().setScene(scene);
        getGameStage().show();

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
                drawCanvas();
                updateLabel();
            }
        };

        instructionsPopUp();
        a.start();
    }

    /**************************************************************************
     * playGame                                                               *
     *                                                                        *
     * Overridden from MiniGame class                                         *
     * Initializes variables for gameplay and starts the timer Thread.        *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *                                                                        *
     * Variables:                                                             *
     * timer - Thread that waits for 3 seconds and then stops the given number*
     *         from being visible. Closes on its own once it is done with     *
     *         resetting values for the round                                 *
     * num - temporary variable that holds the digits of the global variable  *
     *       number while it is being randomly generated                      *
     * rand - double that holds a randomly chosen value for the next digit    *
     *************************************************************************/
    @Override
    public void playGame(){
        Thread timer = new Thread(() -> {
            Object o = new Object();
            synchronized(o){
                try{
                    o.wait(3000);
                    visible = false;
                    setUserPanel = true;
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        int num = 0;
        double rand;
        for(int i = 0; i < numDigits; i++){
            rand = Math.random()*9;
            num += (int)(rand*Math.pow(10, i));
        }
        number = num;
        textField.setText("");
        input = -1;
        timer.start();
    }

    /**************************************************************************
     * updateLabel                                                            *
     *                                                                        *
     * Updates the text of global variable levelLabel to show the most current*
     * information                                                            *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void updateLabel(){ levelLabel.setText("Level "+numDigits); }

    /**************************************************************************
     * getInput                                                               *
     *                                                                        *
     * Parses input from global variable textField and returns the numerical  *
     * value or ends the game if the input was non-numerical.                 *
     *                                                                        *
     * Takes no arguments                                                     *
     * Returns a parsed int value or -1 if the input was invalid or if there  *
     * was nothing input                                                      *
     *                                                                        *
     * Variable:                                                              *
     * input - return value. Holds parsed input from the textField            *
     *************************************************************************/
    private int getInput(){
        int input = -1;
        if(textField.getText().length() != 0){
            try{ input = Integer.parseInt(textField.getText()); }
            catch(NumberFormatException e){ gameOverPopUp(); }
        }
        return input;
    }

    /**************************************************************************
     * drawCanvas                                                             *
     *                                                                        *
     * Draws the displayed values for each state of the screen                *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *                                                                        *
     * Variables:                                                             *
     * gc - GraphicsContext for global variable centerScreen                  *
     * numsCorrect - int[] that tells us if each digit of the input matches   *
     *               the given number                                         *
     * tempInput - holds input while it is being written digit by digit       *
     * numX - holds the x position of the given number on the Canvas based on *
     *        how many digits it has                                          *
     *************************************************************************/
    private void drawCanvas(){
        GraphicsContext gc = centerScreen.getGraphicsContext2D();
        int[] numsCorrect;
        int tempInput = input;
        int numX = 300-(numDigits*15);
        gc.setFill(Color.THISTLE);
        gc.fillRect(0, 0, 600, 250);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(50));
        if(setUserPanel){
            border.setBottom(userPanel);
            setUserPanel = false;
        }
        if(visible){
            gc.fillText(""+number, numX, 120);
        }
        else if(roundEnd){
            gc.fillText(""+number, numX, 80);
            if(tempInput != -1){
                numsCorrect = compareNumbers();
                for(int i = 0; i < numsCorrect.length; i++){
                    if(numsCorrect[i] == 1){ gc.setFill(Color.BLACK); }
                    else{ gc.setFill(Color.RED); }
                    gc.fillText(""+(tempInput%10), numX+
                            ((numsCorrect.length*27)-((i+1)*27)), 180);
                    tempInput /= 10;
                }
            }
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(30));
            gc.fillText("Number", 243, 30);
            gc.fillText("Your Answer", 215, 130);
        }
    }

    /**************************************************************************
     * compareNumbers                                                         *
     *                                                                        *
     * Compares the user input number to the given number by comparing each   *
     * individual digit and marking down which digits are different           *
     *                                                                        *
     * Takes no arguments                                                     *
     * Returns an int[] representing whether each digit in the user input is  *
     * the same as that of the given number. Reads 0 if they are different and*
     * 1 if they are the same.                                                *
     *                                                                        *
     * Variables:                                                             *
     * numsCorrect - return value. Holds a 1 or a 0 for each digit in the user*
     *               input or given number depending on if they have the same *
     *               number of digits                                         *
     * loopBound - number of iterations the for loop will make. Equals the    *
     *             number of digits in the user input                         *
     * tempNum - holds the value of the given number as it is examined digit  *
     *           by digit                                                     *
     * tempInput - holds the value of the user input as it is examined digit  *
     *             by digit                                                   *
     *************************************************************************/
    private int[] compareNumbers(){
        int[] numsCorrect = new int[numDigits];
        int loopBound;
        int tempNum;
        int tempInput;
        if(input == 0){ loopBound = 1; }
        else{ loopBound = (int)(Math.log10(input)+1); }
        if(loopBound > numDigits){ numsCorrect = new int[loopBound]; }
        tempNum = number;
        tempInput = input;
        for(int i = 0; i < loopBound; i++){
            if(i >= numDigits){ numsCorrect[i] = 0; }
            else if((tempNum%10) == (tempInput%10)){ numsCorrect[i] = 1; }
            else{ numsCorrect[i] = 0; }
            tempNum /= 10;
            tempInput /= 10;
        }
        return numsCorrect;
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
        Label instructions = new Label(" Memorize the number shown on the"+
                " screen\n   and type out the number when the field\n  "+
                "becomes visible. Press \"Submit\" to check\n                "+
                "         your answer!");
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

        number = 0;
        numDigits = 1;
        input = -1;
        visible = true;
        roundEnd = false;
        setUserPanel = false;
        border.setBottom(null);

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 400, 175);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
