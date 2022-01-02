import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class that handles the front end UI for Blackjack
 * 
 * @author Darren Seubert
 */
public class FrontEnd extends Application{
    private BackEnd backEnd; // TODO Might need to be static
    private int player1ID;
    private int player2ID;
    private int player3ID;
    private int player4ID;

    public void run(BackEnd engine) {
        this.backEnd = engine;
        Application.launch();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle("Test");

        Group group = new Group();
        Scene scene = new Scene(group,320,240);
        stage.setScene(scene);
        stage.show();
        //setScene();
        // Prompt User for Number of Decks
        // Four TextBox's for possible player ID's
        // A create new player button (Prompts for name and cash)        
    }

    private void setScene() {
    }
}
