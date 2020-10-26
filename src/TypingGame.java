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

public class TypingGame extends MiniGame{
    private VBox text;
    private TextField textField;
    private BorderPane border;
    private boolean gameRunning;
    private boolean timerStarted;
    private long start;
    private int totalChars;

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

        Scene scene = new Scene(border, 685, 645);
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
                        setCurrScore((int)((totalChars/5)/minutesTime));
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
        char[] charText;
        HBox line = new HBox();
        Label tempLabel;
        int numChars = 0;
        int rand = (int)(Math.random()*5);
        gameRunning = true;
        timerStarted = false;
        charText = choosePassage(rand).toCharArray();
        for(char c : charText){
            if(numChars == 45){
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
                "the window to copy the text\n                     on the screen.\n"+
                "              Type as fast as you can!");
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

    /*
     * Passages taken from Dracula, Frankenstein, The Yellow Wallpaper, Jane
     * Eyre, The Picture of Dorian Gray
     */
    private String choosePassage(int i){
        if(i == 0){
            totalChars = 403;
            return "But my very feelings changed to repulsion and terror "+
                    "when I saw the whole man slowly emerge from the window"+
                    " and begin to crawl down the castle wall over that "+
                    "dreadful abyss, face down with his cloak spreading out a"+
                    "round him like great wings. At first I could not believe"+
                    " my eyes. I thought it was some trick of the moonlight, "+
                    "some weird effect of shadow; but I kept looking, and it "+
                    "could be no delusion.";
        }
        else if(i == 1){
            totalChars = 415;
            return "The different accidents of life are not so changeable as "+
                    "the feelings of human nature. I had worked hard for "+
                    "nearly two years, for the sole purpose of infusing life "+
                    "into an inanimate body. For this I had deprived myself "+
                    "of rest and health. I had desired it with an ardour that"+
                    " far exceeded moderation; but now that I had finished, "+
                    "the beauty of the dream vanished, and breathless horror "+
                    "and disgust filled my heart.";
        }
        else if(i == 2){
            totalChars = 453;
            return "On a pattern like this, by daylight, there is a lack of "+
                    "sequence, a defiance of law, that is a constant irritant"+
                    " to a normal mind. The color is hideous enough, and "+
                    "unreliable enough, and infuriating enough, but the "+
                    "pattern is torturing. You think you have mastered it, "+
                    "but just as you get well under way in following, it "+
                    "turns a back somersault and there you are. It slaps you "+
                    "in the face, knocks you down, and tramples upon you. It "+
                    "is like a bad dream.";
        }
        else if(i == 3){
            totalChars = 373;
            return "In the deep shade, at the farther end of the room, a "+
                    "figure ran backwards and forwards. What it was, whether "+
                    "beast or human being, one could not, at first sight, "+
                    "tell: it grovelled, seemingly, on all fours; it snatched"+
                    " and growled like some strange wild animal: but it was "+
                    "covered with clothing, and a quantity of dark, grizzled "+
                    "hair, wild as a mane, hid its head and face.";
        }
        else{
            totalChars = 350;
            return "An exclamation of horror broke from the painter's lips as"+
                    " he saw in the dim light the hideous face on the canvas "+
                    "grinning at him. There was something in its expression "+
                    "that filled him with disgust and loathing. Good heavens!"+
                    " it was Dorian Gray's own face that he was looking at! "+
                    "The horror, whatever it was, had not yet entirely "+
                    "spoiled that marvellous beauty.";
        }
    }
}
