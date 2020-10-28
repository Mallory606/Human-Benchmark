import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NumberMemoryGame extends MiniGame{
    private Label levelLabel;
    private Canvas centerScreen;
    private TextField textField;
    private HBox userPanel;
    private Button nextButton;
    private BorderPane border;
    private int number;
    private int numDigits;
    private int input;
    private boolean visible;
    private boolean roundEnd;
    private boolean setUserPanel;

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

        textField = new TextField("");
        textField.setFont(new Font(20));
        Button submit = new Button("Submit");
        submit.setFont(new Font(20));
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            input = getInput();
            roundEnd = true;
            border.setBottom(nextButton);
        });
        userPanel = new HBox(10);
        userPanel.getChildren().addAll(textField, submit);
        userPanel.setAlignment(Pos.CENTER);

        nextButton = new Button("Next");
        nextButton.setFont(new Font(20));
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean allSame = true;
            for(int i : compareNumbers()){
                if(i == 0){
                    allSame = false;
                    break;
                }
            }
            if(allSame){
                numDigits++;
                roundEnd = false;
                playGame();
            }
            else{ gameOverPopUp(); }
        });

        border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.THISTLE, new CornerRadii(10), new Insets(0))));
        border.setTop(labelPane);
        BorderPane.setAlignment(labelPane, Pos.CENTER);
        border.setCenter(centerScreen);
        BorderPane.setAlignment(centerScreen, Pos.CENTER);
        BorderPane.setAlignment(userPanel, Pos.CENTER);
        BorderPane.setAlignment(nextButton, Pos.CENTER);

        Scene scene = new Scene(border, 600, 400);
        getGameStage().setScene(scene);
        getGameStage().show();

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
                drawCanvas();
            }
        };

        instructionsPopUp();
        a.start();
    }

    @Override
    public void playGame(){
        Thread timer = new Thread(() -> {
            Object o = new Object();
            synchronized(o){
                try{
                    o.wait(3000);
                    visible = false;
                    setUserPanel = true;
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        int num = 0;
        double rand;
        for(int i = 0; i < numDigits; i++){
            rand = Math.random()*9;
            num += (int)(rand*Math.pow(10, i));
        }
        number = num;
        textField.setText("");
        input = -1;
        timer.start();
    }

    private int getInput(){
        int input = -1;
        if(textField.getText().length() != 0){
            try{ input = Integer.parseInt(textField.getText()); }
            catch(NumberFormatException e){ gameOverPopUp(); }
        }
        return input;
    }

    private void drawCanvas(){
        GraphicsContext gc = centerScreen.getGraphicsContext2D();
        int[] numsCorrect;
        int tempInput = input;
        int numX = 300-(numDigits*15);
        gc.setFill(Color.THISTLE);
        gc.fillRect(0, 0, 600, 250);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(50));
        if(setUserPanel){
            border.setBottom(userPanel);
            setUserPanel = false;
        }
        if(visible){
            gc.fillText(""+number, numX, 120);
        }
        else if(roundEnd){
            gc.fillText(""+number, numX, 80);
            if(tempInput != -1){
                numsCorrect = compareNumbers();
                for(int i = 0; i < numDigits; i++){
                    if(numsCorrect[i] == 1){ gc.setFill(Color.BLACK); }
                    else{ gc.setFill(Color.RED); }
                    gc.fillText(""+(tempInput%10), numX+
                            ((numDigits*27)-((i+1)*27)), 180);
                    tempInput /= 10;
                }
            }
            gc.setFont(new Font(30));
            gc.fillText("Number", 243, 30);
            gc.fillText("Your Answer", 215, 130);
        }
    }

    private int[] compareNumbers(){
        int[] numsCorrect = new int[numDigits];
        int loopBound;
        int tempNum;
        int tempInput;
        if(input == 0){ loopBound = 1; }
        else{ loopBound = (int)(Math.log10(input)+1); }
        tempNum = number;
        tempInput = input;
        for(int i = 0; i < loopBound; i++){
            if((tempNum%10) == (tempInput%10)){ numsCorrect[i] = 1; }
            else{ numsCorrect[i] = 0; }
            tempNum /= 10;
            tempInput /= 10;
        }
        return numsCorrect;
    }

    @Override
    public void instructionsPopUp(){
        number = 0;
        numDigits = 1;
        input = -1;
        visible = true;
        roundEnd = false;
        setUserPanel = false;
        playGame();
    }
}
