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

public class StroopTestGame extends MiniGame{
    private Label scoreLabel;
    private Label wordLabel;

    public StroopTestGame(){ super("Stroop Test", " rounds", false); }

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

    @Override
    public void instructionsPopUp(){
        playGame();
    }
}
