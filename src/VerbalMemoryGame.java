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

import java.util.ArrayList;

/******************************************************************************
 * Ashley Krattiger                                                           *
 *                                                                            *
 * VerbalMemoryGame                                                           *
 * This class runs through the Verbal Memory Test from Human Benchmark. Play  *
 * by pressing the "New" button if the shown word has not popped up yet in the*
 * round or "Seen" if you have seen the word already. You have 3 lives and    *
 * lose one every time you make a mistake.                                    *
 *****************************************************************************/
public class VerbalMemoryGame extends MiniGame{
    /**************************************************************************
     * Global Variables:                                                      *
     * dictionary - ArrayList that holds all the words in the word bank       *
     * seenWords - ArrayList that holds all the words that have been shown    *
     *             already in this session                                    *
     * livesLabel - Label that displays how many lives you have left          *
     * scoreLabel - Label that displays your current score                    *
     * wordLabel - Label that displays a word from dictionary on the screen   *
     * lives - int that holds the number of lives you have                    *
     *************************************************************************/
    private ArrayList<String> dictionary;
    private ArrayList<String> seenWords;
    private Label livesLabel;
    private Label scoreLabel;
    private Label wordLabel;
    private int lives;

    /**************************************************************************
     * Constructor - Calls super and provides this game's name                *
     *************************************************************************/
    public VerbalMemoryGame(){ super("Verbal Memory", " rounds", false); }


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
     * seenButton - Button used if a word that has been shown before is shown *
     * newButton - Button used if a word that hasn't been shown before is     *
     *             shown                                                      *
     * buttonBox - HBox that holds the Buttons for the window                 *
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

        livesLabel = new Label("Lives: 3");
        livesLabel.setTextFill(Color.WHITE);
        livesLabel.setFont(new Font(20));
        Label title = new Label("Verbal Memory");
        title.setTextFill(Color.WHITE);
        title.setFont(new Font(30));
        scoreLabel = new Label("Score: 0");
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(new Font(20));
        BorderPane labelPane = new BorderPane();
        labelPane.setLeft(livesLabel);
        BorderPane.setAlignment(livesLabel, Pos.CENTER);
        labelPane.setCenter(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        labelPane.setRight(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.CENTER);

        setDictionary();
        wordLabel = new Label();
        wordLabel.setTextFill(Color.WHITE);
        wordLabel.setFont(new Font(70));
        Button seenButton = new Button("Seen");
        seenButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(!seenWords.contains(wordLabel.getText())){
                lives--;
                if(lives == 0){ gameOverPopUp(); }
                else{
                    seenWords.add(wordLabel.getText());
                    playGame();
                }
            }
            else{
                setCurrScore(getCurrScore()+1);
                playGame();
            }
        });
        seenButton.setFont(new Font(30));
        Button newButton = new Button("New");
        newButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(seenWords.contains(wordLabel.getText())){
                lives--;
                if(lives == 0){ gameOverPopUp(); }
                else{ playGame(); }
            }
            else{
                setCurrScore(getCurrScore()+1);
                seenWords.add(wordLabel.getText());
                playGame();
            }
        });
        newButton.setFont(new Font(30));
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(seenButton, newButton);
        buttonBox.setAlignment(Pos.CENTER);
        VBox centerLayout = new VBox(30);
        centerLayout.getChildren().addAll(wordLabel, buttonBox);
        centerLayout.setAlignment(Pos.CENTER);

        BorderPane border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.DARKMAGENTA, new CornerRadii(10), new Insets(0))));
        border.setTop(labelPane);
        border.setCenter(centerLayout);
        BorderPane.setAlignment(centerLayout, Pos.CENTER);

        Scene scene = new Scene(border, 600, 400);
        getGameStage().setScene(scene);
        getGameStage().show();

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
                updateLabels();
            }
        };

        instructionsPopUp();
        a.start();
    }

    /**************************************************************************
     * playGame                                                               *
     *                                                                        *
     * Overridden from MiniGame class                                         *
     * Randomly generates a word from global variables dictionary or seenWords*
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *                                                                        *
     * Variables:                                                             *
     * randSeen - randomly generated int that determines which list the word  *
     *            comes from                                                  *
     * randWord - randomly generated index that picks a word from either      *
     *            dictionary or seenWords                                     *
     *************************************************************************/
    @Override
    public void playGame(){
        int randSeen = (int)(Math.random()*3);
        int randWord;
        if(seenWords.size() != 0 && randSeen == 1){
            randWord = (int)(Math.random()*seenWords.size());
            wordLabel.setText(seenWords.get(randWord));
        }
        else{
            randWord = (int)(Math.random()*dictionary.size());
            wordLabel.setText(dictionary.get(randWord));
        }
    }

    /**************************************************************************
     * updateLabel                                                            *
     *                                                                        *
     * Updates the text of global variable scoreLabel to show the most current*
     * information                                                            *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void updateLabels(){
        livesLabel.setText("Lives: "+lives);
        scoreLabel.setText("Score: "+getCurrScore());
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
        Label instructions = new Label("  This game tests how many words you "+
                "can\nkeep in short term memory at once. For each \n  word,"+
                " press the button to indicate whether\n     you have seen "+
                "the word already in this \n  session or not. You have 3 "+
                "lives and lose 1 \n                 if you make a mistake.\n"+
                "         Go for as many rounds as you can!");
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

        wordLabel.setText("");
        seenWords = new ArrayList<>();
        lives = 3;
        setCurrScore(0);

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 420, 275);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }

    /**************************************************************************
     * setDictionary                                                          *
     *                                                                        *
     * Adds 100 unique words to global variable dictionary                    *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void setDictionary(){
        dictionary = new ArrayList<>();
        dictionary.add("Prioritize");
        dictionary.add("Daylight");
        dictionary.add("Tempest");
        dictionary.add("Petulant");
        dictionary.add("Clockwise");
        dictionary.add("Turnout");
        dictionary.add("Organize");
        dictionary.add("Undying");
        dictionary.add("Devotion");
        dictionary.add("Intervention");
        dictionary.add("Within");
        dictionary.add("Prestige");
        dictionary.add("Balanced");
        dictionary.add("Injury");
        dictionary.add("Destruction");
        dictionary.add("Infinite");
        dictionary.add("Memorial");
        dictionary.add("Duration");
        dictionary.add("Celebrate");
        dictionary.add("Induce");
        dictionary.add("Hospital");
        dictionary.add("Tombstone");
        dictionary.add("Reflection");
        dictionary.add("Jewelery");
        dictionary.add("Discord");
        dictionary.add("Window");
        dictionary.add("Canine");
        dictionary.add("Console");
        dictionary.add("Vanity");
        dictionary.add("Escape");
        dictionary.add("Remember");
        dictionary.add("Shadow");
        dictionary.add("Rancor");
        dictionary.add("Nemesis");
        dictionary.add("Lullaby");
        dictionary.add("Oblivious");
        dictionary.add("Predator");
        dictionary.add("Agitated");
        dictionary.add("Warden");
        dictionary.add("Endurance");
        dictionary.add("Factory");
        dictionary.add("Nobody");
        dictionary.add("Insidious");
        dictionary.add("Survivor");
        dictionary.add("Killer");
        dictionary.add("Warzone");
        dictionary.add("Pumpkin");
        dictionary.add("Interruption");
        dictionary.add("Subscribe");
        dictionary.add("Packing");
        dictionary.add("Basement");
        dictionary.add("Determined");
        dictionary.add("Marking");
        dictionary.add("Altruism");
        dictionary.add("Camping");
        dictionary.add("Madness");
        dictionary.add("Goddess");
        dictionary.add("Silence");
        dictionary.add("Cutlery");
        dictionary.add("Continuous");
        dictionary.add("Splatter");
        dictionary.add("Simulate");
        dictionary.add("Experience");
        dictionary.add("Between");
        dictionary.add("Chamomile");
        dictionary.add("Tendency");
        dictionary.add("Golden");
        dictionary.add("Diamond");
        dictionary.add("Platinum");
        dictionary.add("Magician");
        dictionary.add("Hermit");
        dictionary.add("Chariot");
        dictionary.add("Hierophant");
        dictionary.add("Emerald");
        dictionary.add("Worldly");
        dictionary.add("Company");
        dictionary.add("Moonlight");
        dictionary.add("Silver");
        dictionary.add("Revelation");
        dictionary.add("Archery");
        dictionary.add("Internet");
        dictionary.add("Slasher");
        dictionary.add("Furtive");
        dictionary.add("Soaking");
        dictionary.add("Mystery");
        dictionary.add("Sacrifice");
        dictionary.add("Fortune");
        dictionary.add("Viewer");
        dictionary.add("Masquerade");
        dictionary.add("Shipment");
        dictionary.add("Devour");
        dictionary.add("Blighted");
        dictionary.add("Gunslinger");
        dictionary.add("Pyramid");
        dictionary.add("Plagued");
        dictionary.add("Legion");
        dictionary.add("Buffered");
        dictionary.add("Compound");
        dictionary.add("Corruption");
        dictionary.add("Powerful");
        dictionary.add("Surprise");
    }
}
