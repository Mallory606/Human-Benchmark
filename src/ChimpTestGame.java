import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChimpTestGame extends MiniGame{
    private int[][] gameBoard;

    public ChimpTestGame(){ super("Chimp Test"); }

    @Override
    public void initializeWindow(Stage primaryStage){
        Stage chimpTestStage = new Stage();
        chimpTestStage.initModality(Modality.APPLICATION_MODAL);
        chimpTestStage.initOwner(primaryStage);
        chimpTestStage.setAlwaysOnTop(true);
        chimpTestStage.setTitle(getName());

        gameBoard = new int[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                gameBoard[i][j] = 0;
            }
        }


    }

    @Override
    public void playGame(){

    }
}
