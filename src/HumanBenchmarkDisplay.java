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
    private MiniGame[] row1Games;
    private MiniGame[] row2Games;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Human Benchmark");

        row1Games = new MiniGame[4];
        row2Games = new MiniGame[4];

        Label welcomeLabel = new Label("Welcome to the Human Benchmark!");
        welcomeLabel.setFont(new Font(50));
        welcomeLabel.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(5);
        mainVBox.setAlignment(Pos.CENTER);
        HBox row1HBox = new HBox(5);
        row1HBox.setAlignment(Pos.CENTER);
        fillMainHBox(row1HBox, 1);
        HBox row2HBox = new HBox(5);
        row2HBox.setAlignment(Pos.CENTER);
        fillMainHBox(row2HBox, 2);
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
    }

    private void fillMainHBox(HBox hBox, int row){
        AnchorPane anchor;
        Canvas currCanvas;
        GraphicsContext gc;
        Button currButton;
        if(row == 1){
            row1Games[0] = new ReactionTimeGame();
            row1Games[1] = new AimTrainerGame();
            row1Games[2] = new ChimpTestGame();
            row1Games[3] = new VisualMemoryGame();
        }
        else{
            row2Games[0] = new TypingGame();
            row1Games[1] = new NumberMemoryGame();
            row1Games[2] = new VerbalMemoryGame();
            //row1Games[3] = new VisualMemoryGame();
        }
        for(int i = 0; i < 4; i++){
            currCanvas = new Canvas(200, 175);
            gc = currCanvas.getGraphicsContext2D();
            gc.setFill(Color.HONEYDEW);
            gc.setStroke(Color.FORESTGREEN);
            gc.setLineWidth(10);
            gc.fillRoundRect(0, 0, 200, 175, 25, 25);
            gc.strokeRoundRect(0, 0, 200, 175, 25, 25);
            currButton = new Button("Play");
            anchor = new AnchorPane(currCanvas, currButton);
            AnchorPane.setTopAnchor(currCanvas, 0.0);
            AnchorPane.setLeftAnchor(currCanvas, 0.0);
            AnchorPane.setRightAnchor(currCanvas, 0.0);
            AnchorPane.setBottomAnchor(currCanvas, 0.0);
            AnchorPane.setTopAnchor(currButton, 110.0);
            AnchorPane.setLeftAnchor(currButton, 70.0);
            AnchorPane.setRightAnchor(currButton, 70.0);
            AnchorPane.setBottomAnchor(currButton, 35.0);
            hBox.getChildren().add(anchor);
        }
    }
}