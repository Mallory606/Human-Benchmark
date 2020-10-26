import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/******************************************************************************
 * Ashley Krattiger                                                           *
 *                                                                            *
 * HumanBenchmarkDisplay                                                      *
 * Contains main                                                              *
 * This class initializes the window for the main menu of the application and *
 * initializes all MiniGames and other necessary processes.                   *
 * The main menu acts as a portal allowing you to access the games and keeps  *
 * a high score for each game.                                                *
 *****************************************************************************/
public class HumanBenchmarkDisplay extends javafx.application.Application{
    /**************************************************************************
     * Global Variables:                                                      *
     * pStage - copy of the primaryStage from function start. Used to pass on *
     *          to MiniGames                                                  *
     * games - array that holds each MiniGame in the order they are displayed *
     *         on the window                                                  *
     * scoreLabels - array that holds a Label reading the current high score  *
     *               for each game in the order they are displayed on the     *
     *               window                                                   *
     *************************************************************************/
    private Stage pStage;
    private MiniGame[][] games;
    private Label[][] scoreLabels;

    /**************************************************************************
     * start                                                                  *
     *                                                                        *
     * Overridden from Application                                            *
     * Initializes the main menu window and starts the AnimationTimer         *
     *                                                                        *
     * @param primaryStage - Stage for the main menu window                   *
     * Returns nothing                                                        *
     * @throws Exception - throws Exception                                   *
     *                                                                        *
     * Variables:                                                             *
     * welcomeLabel - Label that welcomes the user to the Human Benchmark     *
     * mainVBox - VBox that holds grid of visuals corresponding with MiniGames*
     * row1HBox - HBox that holds visuals for first row of MiniGames          *
     * row2HBox - HBox that holds visuals for second row of MiniGames         *
     * border - BorderPane that organizes the Nodes for the main menu window  *
     * scene - Scene for the main menu window                                 *
     * a - AnimationTimer that updates the visuals of the main menu window    *
     *************************************************************************/
    @Override
    public void start(Stage primaryStage) throws Exception{
        pStage = primaryStage;
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
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
        border.setCenter(mainVBox);
        BorderPane.setAlignment(mainVBox, Pos.CENTER);

        Scene scene = new Scene(border, 855, 480);
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
                updateScores();
            }
        };
        a.start();
    }

    /**************************************************************************
     * fillMainHBox                                                           *
     *                                                                        *
     * Fills the given HBox with Canvases and draws each game's information   *
     * based on the games stored in global variable games                     *
     *                                                                        *
     * @param hBox - HBox that needs to be filled                             *
     * @param row - represents which row of the grid we are filling out       *
     * Returns nothing                                                        *
     *                                                                        *
     * Variables:                                                             *
     * anchor - cycles through an AnchorPane for each game represented in the *
     *          row                                                           *
     * currCanvas - cycles through a Canvas for each game represented in the  *
     *              row                                                       *
     * gc - cycles though the GraphicsContext for each Canvas currCanvas holds*
     * currLabel - cycles through a Label for each game represented in the row*
     * currButton - cycles through a Button for each game represented in the  *
     *              row                                                       *
     * finalRow - final copy of parameter row to use in eventHandlers         *
     * leftAnchorDist - array that holds the left anchor inset for each Label *
     *                  held by currLabel. These correspond with the order of *
     *                  MiniGames in global variable games                    *
     * y - final copy of index i to use in eventHandlers                      *
     *************************************************************************/
    private void fillMainHBox(HBox hBox, int row){
        AnchorPane anchor;
        Canvas currCanvas;
        GraphicsContext gc;
        Label currLabel;
        Button currButton;
        final int finalRow = row;
        double[][] leftAnchorDist = new double[][]{{25.0, 38.0, 40.0, 17.0},
                                                    {60.0, 5.0, 15.0, 35.0}};

        for(int i = 0; i < 4; i++){
            final int y = i;
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
            currButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                games[finalRow][y].initializeWindow(pStage);
            });
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

    /**************************************************************************
     * updateScores                                                           *
     *                                                                        *
     * Updates the values of the Labels in global variable scoreLabels to     *
     * reflect the most current information                                   *
     *                                                                        *
     * Takes no arguments, returns nothing                                    *
     *************************************************************************/
    private void updateScores(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 4; j++){
                scoreLabels[i][j].setText("High Score: "
                        + games[i][j].getHighScore());
            }
        }
    }
}
