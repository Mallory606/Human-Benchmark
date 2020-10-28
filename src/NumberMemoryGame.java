import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NumberMemoryGame extends MiniGame{
    private Label levelLabel;
    private Canvas centerScreen;
    private TextField textField;
    private int number;

    public NumberMemoryGame(){ super("Number Memory", " rounds", false); }

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

        textField = new TextField();
        textField.setFont(new Font(20));
        Button submit = new Button("Submit");
        submit.setFont(new Font(20));
        HBox userPanel = new HBox(10);
        userPanel.getChildren().addAll(textField, submit);
        userPanel.setAlignment(Pos.CENTER);

        BorderPane border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.THISTLE, new CornerRadii(10), new Insets(0))));
        border.setTop(labelPane);
        BorderPane.setAlignment(labelPane, Pos.CENTER);
        border.setCenter(centerScreen);
        BorderPane.setAlignment(centerScreen, Pos.CENTER);
        border.setBottom(userPanel);
        BorderPane.setAlignment(userPanel, Pos.CENTER);

        Scene scene = new Scene(border, 600, 400);
        getGameStage().setScene(scene);
        getGameStage().show();

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
            }
        };
        a.start();
    }

    @Override
    public void playGame(){

    }

    @Override
    public void instructionsPopUp(){

    }
}
