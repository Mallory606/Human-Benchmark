import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
 * StroopTestGame                                                             *
 * This class runs through the Stroop Test, a psychological test measuring the*
 * ability to respond properly when being given conflicting information. The  *
 * screen will show the name of a color while written in a font of a different*
 * color. You are to respond by pressing the button labeled with the same     *
 * color as the font (not the name).                                          *
 *****************************************************************************/
public class StroopTestGame extends MiniGame{
    /**************************************************************************
     * Global Variables:                                                      *
     * scoreLabel - Label that displays your current score                    *
     * wordLabel - Label that displays the word on the screen                 *
     *************************************************************************/
    private Label scoreLabel;
    private Label wordLabel;

    /**************************************************************************
     * Constructor - Calls super and provides this game's name                *
     *************************************************************************/
    public StroopTestGame(){ super("Stroop Test", " rounds", false); }

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
     * labelPane - BorderPane that holds the Labels at the top of the screen  *
     * blackButton - Button for if the color is black                         *
     * redButton - Button for if the color is red                             *
     * yellowButton - Button for if the color is yellow                       *
     * greenButton - Button for if the color is green                         *
     * blueButton - Button for if the color is blue                           *
     * purpleButton - Button for if the color is purple                       *
     * buttonPane - FlowPane that holds the color buttons                     *
     * centerLayout - VBox that holds the Nodes displayed in the center of the*
     *                window                                                  *
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

        Label title = new Label("Stroop Test");
        title.setFont(new Font(30));
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font(20));
        BorderPane labelPane = new BorderPane();
        labelPane.setCenter(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        labelPane.setRight(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.CENTER);

        wordLabel = new Label();
        wordLabel.setFont(new Font(70));
        Button blackButton = new Button("Black");
        blackButton.setFont(new Font(20));
        blackButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if(wordLabel.getTextFill() == Color.BLACK){
                setCurrScore(getCurrScore()+1);
                if(getCurrScore() == 100){ gameOverPopUp(); }
                playGame();
            }
            else{ gameOverPopUp(); }
        });
        Button redButton = new Button("Red");
        redButton.setFont(new Font(20));
        redButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if(wordLabel.getTextFill() == Color.RED){
                setCurrScore(getCurrScore()+1);
                if(getCurrScore() == 100){ gameOverPopUp(); }
                playGame();
            }
            else{ gameOverPopUp(); }
        });
        Button yellowButton = new Button("Yellow");
        yellowButton.setFont(new Font(20));
        yellowButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if(wordLabel.getTextFill() == Color.GOLD){
                setCurrScore(getCurrScore()+1);
                if(getCurrScore() == 100){ gameOverPopUp(); }
                playGame();
            }
            else{ gameOverPopUp(); }
        });
        Button greenButton = new Button("Green");
        greenButton.setFont(new Font(20));
        greenButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if(wordLabel.getTextFill() == Color.GREEN){
                setCurrScore(getCurrScore()+1);
                if(getCurrScore() == 100){ gameOverPopUp(); }
                playGame();
            }
            else{ gameOverPopUp(); }
        });
        Button blueButton = new Button("Blue");
        blueButton.setFont(new Font(20));
        blueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if(wordLabel.getTextFill() == Color.BLUE){
                setCurrScore(getCurrScore()+1);
                if(getCurrScore() == 100){ gameOverPopUp(); }
                playGame();
            }
            else{ gameOverPopUp(); }
        });
        Button purpleButton = new Button("Purple");
        purpleButton.setFont(new Font(20));
        purpleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if(wordLabel.getTextFill() == Color.DARKVIOLET){
                setCurrScore(getCurrScore()+1);
                if(getCurrScore() == 100){ gameOverPopUp(); }
                playGame();
            }
            else{ gameOverPopUp(); }
        });
        FlowPane buttonPane = new FlowPane(30.0, 20.0);
        buttonPane.getChildren().addAll(blackButton, redButton, yellowButton,
                purpleButton, greenButton, blueButton);
        buttonPane.setAlignment(Pos.CENTER);
        VBox centerLayout = new VBox(30);
        centerLayout.getChildren().addAll(wordLabel, buttonPane);
        centerLayout.setAlignment(Pos.CENTER);

        BorderPane border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.LAVENDERBLUSH, new CornerRadii(10), new Insets(0))));
        border.setTop(labelPane);
        border.setCenter(centerLayout);
        BorderPane.setAlignment(centerLayout, Pos.CENTER);

        Scene scene = new Scene(border, 500, 400);
        getGameStage().setScene(scene);
        getGameStage().show();

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
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
     * Randomly generates a word and color to display                         *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *                                                                        *
     * Variables:                                                             *
     * randWord - randomly generated index that picks a word                  *
     * randColor - randomly generated index that picks a color                *
     *************************************************************************/
    @Override
    public void playGame(){
        int randWord = (int)(Math.random()*6);
        int randColor = (int)(Math.random()*6);
        if(randWord == 0){ wordLabel.setText("black"); }
        else if(randWord == 1){ wordLabel.setText("red"); }
        else if(randWord == 2){ wordLabel.setText("yellow"); }
        else if(randWord == 3){ wordLabel.setText("green"); }
        else if(randWord == 4){ wordLabel.setText("blue"); }
        else{ wordLabel.setText("purple"); }
        if(randColor == 0){ wordLabel.setTextFill(Color.BLACK); }
        else if(randColor == 1){ wordLabel.setTextFill(Color.RED); }
        else if(randColor == 2){ wordLabel.setTextFill(Color.GOLD); }
        else if(randColor == 3){ wordLabel.setTextFill(Color.GREEN); }
        else if(randColor == 4){ wordLabel.setTextFill(Color.BLUE); }
        else{ wordLabel.setTextFill(Color.DARKVIOLET); }
    }

    /**************************************************************************
     * updateLabels                                                           *
     *                                                                        *
     * Updates the text of global variable scoreLabel to show the most current*
     * information                                                            *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void updateLabel(){ scoreLabel.setText("Score: "+getCurrScore()); }

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
        Label instructions = new Label("      This game tests your ability to"+
                " give\n   appropriate responses when the brain is\n "+
                "receiving conflicting signals. A word will be\nshown in the "+
                "center of the screen. Press the \n  button that matches the "+
                "font color of the \n       word, not the color the word "+
                "spells.\n              Answer as fast as you can!");
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

        setCurrScore(0);

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 420, 275);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
