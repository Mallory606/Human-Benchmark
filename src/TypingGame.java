import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class TypingGame extends MiniGame{
    private VBox text;
    private TextField textField;
    private BorderPane border;
    private boolean gameRunning;
    private boolean startTimer;
    private boolean timerStarted;
    private long start;

    public TypingGame(){ super("Typing"); }

    @Override
    public void initializeWindow(Stage primaryStage) {
        setGameStage(new Stage());
        getGameStage().initModality(Modality.APPLICATION_MODAL);
        getGameStage().initOwner(primaryStage);
        getGameStage().setAlwaysOnTop(true);
        getGameStage().setTitle(getName());

        Label title = new Label("Typing Test");
        title.setFont(new Font(50));
        text = new VBox(5);
        textField = new TextField();

        border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.KHAKI, new CornerRadii(10), new Insets(0))));
        border.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        border.setCenter(text);
        BorderPane.setAlignment(text, Pos.CENTER);
        border.setBottom(textField);
        BorderPane.setAlignment(textField, Pos.CENTER);

        Scene scene = new Scene(border, 685, 585);
        getGameStage().setScene(scene);
        getGameStage().show();


        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
                boolean gameOver;
                long elapsedTime;
                double minutesTime;
                if(gameRunning){
                    gameOver = checkTyping();
                    if(gameOver){
                        elapsedTime = System.nanoTime() - start;
                        minutesTime = elapsedTime/(6e+10);
                        setCurrScore((int)((15/5)/minutesTime));
                        gameRunning = false;
                        gameOverPopUp();
                    }
                }
            }
        };
        a.start();

        instructionsPopUp();
    }

    @Override
    public void playGame(){
        char[] charText = "12345678901234567890123456789012345678901234567890".toCharArray();
        HBox line = new HBox();
        Label tempLabel;
        int numChars = 0;
        gameRunning = true;
        startTimer = false;
        timerStarted = false;
        for(char c : charText){
            if(numChars == 40){
                text.getChildren().add(line);
                line = new HBox();
                numChars = 0;
            }
            numChars++;
            tempLabel = new Label("" + c);
            tempLabel.setFont(new Font(30));
            line.getChildren().add(tempLabel);
        }
        text.getChildren().add(line);
    }

    private boolean checkTyping(){
        char[] input = textField.getText().toCharArray();
        boolean allCorrect = true;
        int i = 0;
        HBox line;
        String tempChar;
        resetTextColor();
        if(input.length == 0){ return false; }
        else if(!timerStarted){
            start = System.nanoTime();
            timerStarted = true;
        }
        for(Node n : text.getChildren()){
            line = (HBox)n;
            for(Node c : line.getChildren()){
                tempChar = ((Label)c).getText();
                if(i >= input.length){ return false; }
                if(!tempChar.equals(input[i] + "")){
                    ((Label)c).setTextFill(Color.RED);
                    allCorrect = false;
                }
                else{ ((Label)c).setTextFill(Color.LIMEGREEN); }
                i++;
            }
        }
        return allCorrect;
    }

    private void resetTextColor(){
        HBox line;
        for(Node n : text.getChildren()){
            line = (HBox)n;
            for(Node c : line.getChildren()){
                ((Label)c).setTextFill(Color.BLACK);
            }
        }
    }

    @Override
    public void instructionsPopUp(){
        Stage instructionsStage = new Stage();
        Label instructions = new Label(" This game will test how fast you"+
                " type in\n  words per minute. Use the field at the\n bottom of "+
                "the window to copy the text\n                      on the screen.\n"+
                "\n                          Type as fast as you can!");
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
            gameRunning = true;
            playGame();
        });

        text = new VBox(5);
        border.setCenter(text);
        BorderPane.setAlignment(text, Pos.CENTER);
        textField.setText("");

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 400, 200);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
