import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/******************************************************************************
 * Ashley Krattiger                                                           *
 *                                                                            *
 * MiniGame                                                                   *
 * This class holds the fields shared by all the games from the Human         *
 * Benchmark website. Holds the function that initializes the Game Over pop up*
 * that appears when a game ends.                                             *
 *****************************************************************************/
public abstract class MiniGame{
    /**************************************************************************
     * Global Variables:                                                      *
     * name - String that represents the name of the game                     *
     * scoreUnit - String that represents what unit of measure the final score*
     *             is in                                                      *
     * inverseScore - boolean that keeps track of whether the largest number  *
     *                or the smallest number should be kept as the high score.*
     *                False if you want the larger score, true if smaller     *
     * highScore - int that holds the highest score a person has earned so far*
     * currScore - int that holds the score the user earned in the most recent*
     *             playthrough of the game                                    *
     * gameStage - the Stage for the game play window                         *
     *************************************************************************/
    private final String name;
    private final String scoreUnit;
    private final boolean inverseScore;
    private int highScore;
    private int currScore;
    private Stage gameStage;

    /**************************************************************************
     * Constructor                                                            *
     *                                                                        *
     * Sets global variables                                                  *
     *                                                                        *
     * @param n - name of the game                                            *
     * @param unit - unit of measure for the score                            *
     * @param inverse - true if high score should be the smallest value       *
     *************************************************************************/
    public MiniGame(String n, String unit, boolean inverse){
        name = n;
        scoreUnit = unit;
        inverseScore = inverse;
        highScore = 0;
        currScore = 0;
    }

    /**************************************************************************
     * playGame - Abstract Class                                              *
     *                                                                        *
     * Runs processes needed for gameplay                                     *
     *************************************************************************/
    public abstract void playGame();

    /**************************************************************************
     * initializeWindow - Abstract Class                                      *
     *                                                                        *
     * Initializes the gameplay window for this game                          *
     *************************************************************************/
    public abstract void initializeWindow(Stage primaryStage);

    /**************************************************************************
     * instructionsPopUp - Abstract Class                                     *
     *                                                                        *
     * Initializes the instructions window and handles steps necessary for    *
     * restarting the game                                                    *
     *************************************************************************/
    public abstract void instructionsPopUp();

    /**************************************************************************
     * getName - Getter                                                       *
     *                                                                        *
     * Returns the name of the game                                           *
     *************************************************************************/
    public String getName() { return name; }

    /**************************************************************************
     * getHighScore - Getter                                                  *
     *                                                                        *
     * Returns the high score                                                 *
     *************************************************************************/
    public int getHighScore(){ return highScore; }

    /**************************************************************************
     * getCurrScore - Getter                                                  *
     *                                                                        *
     * Returns the current score                                              *
     *************************************************************************/
    public int getCurrScore(){ return currScore; }

    /**************************************************************************
     * setCurrScore - Setter                                                  *
     *                                                                        *
     * Sets the current score                                                 *
     *************************************************************************/
    public void setCurrScore(int score){ currScore = score; }

    /**************************************************************************
     * setGameStage - Setter                                                  *
     *                                                                        *
     * Sets the Stage for gameplay                                            *
     *************************************************************************/
    public void setGameStage(Stage gStage) { gameStage = gStage; }

    /**************************************************************************
     * getGameStage - Getter                                                  *
     *                                                                        *
     * Returns the Stage for gameplay                                         *
     *************************************************************************/
    public Stage getGameStage() { return gameStage; }

    /**************************************************************************
     * gameOverPopUp                                                          *
     *                                                                        *
     * Initializes the game over pop up window and handles storing new high   *
     * scores.                                                                *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *                                                                        *
     * Variables:                                                             *
     * gameOverStage - Stage for the game over pop up window                  *
     * finalScore - Label that reads out the user's ending score              *
     * highScoreLabel - Label that declares that there is a new high score    *
     * buttonPanel - HBox that holds the Buttons for the pop up               *
     * retryButton - Button that restarts the game to play again              *
     * backButton - Button that returns the user to the main menu window      *
     * border - BorderPane for the game over pop up                           *
     * scene - Scene for the game over pop up                                 *
     *************************************************************************/
    public void gameOverPopUp(){
        Stage gameOverStage = new Stage();
        Label finalScore = new Label("Final Score: "+currScore+scoreUnit);
        Label highScoreLabel = new Label("New High Score!");
        HBox buttonPanel = new HBox(10);
        Button retryButton = new Button("Retry");
        Button backButton = new Button("Back");
        BorderPane border = new BorderPane();
        Scene scene;
        gameOverStage.initModality(Modality.APPLICATION_MODAL);
        gameOverStage.initOwner(gameStage);
        gameOverStage.setAlwaysOnTop(true);
        gameOverStage.setTitle("Game Over");
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gameStage.close();
            gameOverStage.close();
        });
        retryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            instructionsPopUp();
            gameOverStage.close();
        });
        buttonPanel.getChildren().addAll(retryButton, backButton);
        buttonPanel.setAlignment(Pos.CENTER);
        finalScore.setFont(new Font(20));
        highScoreLabel.setFont(new Font(20));
        border.setCenter(finalScore);
        border.setBottom(buttonPanel);
        BorderPane.setAlignment(buttonPanel, Pos.CENTER);
        if(currScore > highScore && !inverseScore){
            highScore = currScore;
            border.setTop(highScoreLabel);
            BorderPane.setAlignment(highScoreLabel, Pos.CENTER);
        }
        if(currScore<highScore && inverseScore|| inverseScore && highScore==0){
            highScore = currScore;
            border.setTop(highScoreLabel);
            BorderPane.setAlignment(highScoreLabel, Pos.CENTER);
        }
        scene = new Scene(border, 200, 150);
        gameOverStage.setScene(scene);
        gameOverStage.show();
    }
}
