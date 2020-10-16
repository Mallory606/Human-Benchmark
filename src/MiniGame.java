import javafx.stage.Stage;

public abstract class MiniGame{
    private final String name;
    private int highScore;
    private int currScore;

    public MiniGame(String n){
        name = n;
        highScore = 0;
        currScore = 0;
    }

    public abstract void playGame();

    public abstract void initializeWindow(Stage primaryStage);

    public String getName() { return name; }

    public int getHighScore(){ return highScore; }

    public void setHighScore(int score){ highScore = score; }

    public int getCurrScore(){ return currScore; }

    public void setCurrScore(int score){ currScore = score; }
}
