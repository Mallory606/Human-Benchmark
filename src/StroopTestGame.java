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

public class StroopTestGame extends MiniGame{
    private Label livesLabel;
    private Label scoreLabel;
    private Label wordLabel;
    private int lives;

    public StroopTestGame(){ super("Stroop Test", " rounds", false); }

    @Override
    public void initializeWindow(Stage primaryStage){
        setGameStage(new Stage());
        getGameStage().initModality(Modality.APPLICATION_MODAL);
        getGameStage().initOwner(primaryStage);
        getGameStage().setAlwaysOnTop(true);
        getGameStage().setTitle(getName());

        livesLabel = new Label("Lives: 3");
        livesLabel.setFont(new Font(20));
        Label title = new Label("Stroop Test");
        title.setFont(new Font(30));
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font(20));
        BorderPane labelPane = new BorderPane();
        labelPane.setLeft(livesLabel);
        BorderPane.setAlignment(livesLabel, Pos.CENTER);
        labelPane.setCenter(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        labelPane.setRight(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.CENTER);

        wordLabel = new Label();
        wordLabel.setFont(new Font(70));
        Button blackButton = new Button("Black");
        blackButton.setFont(new Font(20));
        Button redButton = new Button("Red");
        redButton.setFont(new Font(20));
        Button yellowButton = new Button("Yellow");
        yellowButton.setFont(new Font(20));
        Button greenButton = new Button("Green");
        greenButton.setFont(new Font(20));
        Button blueButton = new Button("Blue");
        blueButton.setFont(new Font(20));
        Button purpleButton = new Button("Purple");
        purpleButton.setFont(new Font(20));
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
     * updateLabels                                                           *
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
        lives = 3;
    }
}
