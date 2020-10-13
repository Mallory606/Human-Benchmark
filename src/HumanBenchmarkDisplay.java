import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HumanBenchmarkDisplay extends javafx.application.Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Human Benchmark");

        Label welcomeLabel = new Label("Welcome to the Human Benchmark!");
        welcomeLabel.setFont(new Font(50));
        welcomeLabel.setAlignment(Pos.CENTER);

        BorderPane border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.LIGHTGREEN, new CornerRadii(10), new Insets(0))));
        border.setAlignment(welcomeLabel, Pos.CENTER);
        border.setTop(welcomeLabel);

        Scene scene = new Scene(border, 855, 420);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
