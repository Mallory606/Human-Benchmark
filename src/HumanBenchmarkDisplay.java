import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HumanBenchmarkDisplay extends javafx.application.Application{
    private MiniGame[][] games;
    private Label[][] scoreLabels;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Human Benchmark");

        games = new MiniGame[2][4];
        scoreLabels = new Label[2][4];
        games[0][0] = new ReactionTimeGame();
        games[0][1] = new AimTrainerGame();
        games[0][2] = new ChimpTestGame();
        games[0][3] = new VisualMemoryGame();
        games[1][0] = new TypingGame();
        games[1][1] = new NumberMemoryGame();
        games[1][2] = new VerbalMemoryGame();
        games[1][3] = new StroopTestGame();

        Label welcomeLabel = new Label("Welcome to the Human Benchmark!");
        welcomeLabel.setFont(new Font(50));
        welcomeLabel.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(5);
        mainVBox.setAlignment(Pos.CENTER);
        HBox row1HBox = new HBox(5);
        row1HBox.setAlignment(Pos.CENTER);
        fillMainHBox(row1HBox, 0);
        HBox row2HBox = new HBox(5);
        row2HBox.setAlignment(Pos.CENTER);
        fillMainHBox(row2HBox, 1);
        mainVBox.getChildren().addAll(row1HBox, row2HBox);

        BorderPane border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.LIGHTGREEN, new CornerRadii(10), new Insets(0))));
        border.setTop(welcomeLabel);
        border.setAlignment(welcomeLabel, Pos.CENTER);
        border.setCenter(mainVBox);
        border.setAlignment(mainVBox, Pos.CENTER);

        Scene scene = new Scene(border, 855, 480);
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer a = new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        };
        a.start();
    }

    private void fillMainHBox(HBox hBox, int row){
        AnchorPane anchor;
        Canvas currCanvas;
        GraphicsContext gc;
        Label currLabel;
        Button currButton;
        double[][] leftAnchorDist = new double[][]{{25.0, 38.0, 40.0, 17.0},
                                                    {60.0, 5.0, 15.0, 35.0}};

        for(int i = 0; i < 4; i++){
            currCanvas = new Canvas(200, 175);
            gc = currCanvas.getGraphicsContext2D();
            gc.setFill(Color.HONEYDEW);
            gc.setStroke(Color.FORESTGREEN);
            gc.setLineWidth(10);
            gc.fillRoundRect(0, 0, 200, 175, 25, 25);
            gc.strokeRoundRect(0, 0, 200, 175, 25, 25);
            currLabel = new Label(games[row][i].getName());
            currLabel.setFont(new Font(25));
            scoreLabels[row][i] = new Label("High Score: " +
                    games[row][i].getHighScore());
            scoreLabels[row][i].setFont(new Font(15));
            currButton = new Button("Play");
            anchor = new AnchorPane(currCanvas, currLabel,
                    scoreLabels[row][i], currButton);
            AnchorPane.setTopAnchor(currCanvas, 0.0);
            AnchorPane.setLeftAnchor(currCanvas, 0.0);
            AnchorPane.setRightAnchor(currCanvas, 0.0);
            AnchorPane.setBottomAnchor(currCanvas, 0.0);
            AnchorPane.setTopAnchor(currLabel, 0.0);
            AnchorPane.setLeftAnchor(currLabel, leftAnchorDist[row][i]);
            AnchorPane.setRightAnchor(currLabel, 0.0);
            AnchorPane.setBottomAnchor(currLabel, 60.0);
            AnchorPane.setTopAnchor(scoreLabels[row][i], 0.0);
            AnchorPane.setLeftAnchor(scoreLabels[row][i], 55.0);
            AnchorPane.setRightAnchor(scoreLabels[row][i], 0.0);
            AnchorPane.setBottomAnchor(scoreLabels[row][i], 0.0);
            AnchorPane.setTopAnchor(currButton, 110.0);
            AnchorPane.setLeftAnchor(currButton, 70.0);
            AnchorPane.setRightAnchor(currButton, 70.0);
            AnchorPane.setBottomAnchor(currButton, 35.0);
            hBox.getChildren().add(anchor);
        }
    }
}
