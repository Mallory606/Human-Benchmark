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

public abstract class MiniGame{
    private final String name;
    private final String scoreUnit;
    private final boolean inverseScore;
    private int highScore;
    private int currScore;
    private Stage gameStage;
    private Button retryButton;

    public MiniGame(String n, String unit, boolean inverse){
        name = n;
        scoreUnit = unit;
        inverseScore = inverse;
        highScore = 0;
        currScore = 0;
    }

    public abstract void playGame();

    public abstract void initializeWindow(Stage primaryStage);

    public abstract void instructionsPopUp();

    public String getName() { return name; }

    public int getHighScore(){ return highScore; }

    public void setHighScore(int score){ highScore = score; }

    public int getCurrScore(){ return currScore; }

    public void setCurrScore(int score){ currScore = score; }

    public void setGameStage(Stage gStage) { gameStage = gStage; }

    public Stage getGameStage() { return gameStage; }

    public Button getRetryButton() { return retryButton; }

    public void gameOverPopUp(){
        Stage gameOverStage = new Stage();
        Label finalScore = new Label("Final Score: "+currScore+scoreUnit);
        Label highScoreLabel = new Label("New High Score!");
        HBox buttonPanel = new HBox(10);
        retryButton = new Button("Retry");
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
