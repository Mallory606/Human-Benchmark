import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VerbalMemoryGame extends MiniGame{
    private ArrayList<String> dictionary;
    private ArrayList<String> seenWords;
    private Label livesLabel;
    private Label scoreLabel;
    private Label wordLabel;
    private int lives;

    public VerbalMemoryGame(){ super("Verbal Memory", " words", false); }


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
        wordLabel = new Label("Daylight");
        wordLabel.setTextFill(Color.WHITE);
        wordLabel.setFont(new Font(70));
        Button seenButton = new Button("Seen");
        seenButton.setFont(new Font(30));
        Button newButton = new Button("New");
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

    @Override
    public void playGame(){

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

    @Override
    public void instructionsPopUp(){
        seenWords = new ArrayList<>();
        lives = 3;
    }

    // Current number of words: 20
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
    }
}
