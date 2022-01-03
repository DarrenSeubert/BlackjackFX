import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Class that handles the front end UI for Blackjack
 * 
 * @author Darren Seubert
 */
public class FrontEnd extends Application{
    private static BackEnd backEnd;
    private Stage mStage;
    private String greetingString;
    private int numOfDecks;
    //private int player1ID;
    //private int player2ID;
    //private int player3ID;
    //private int player4ID;

    /**
     * 
     */
    public FrontEnd() {
        greetingString = "Welcome to BlackjackFX!\nEnter Number of Decks:";
    }

    /**
     * 
     * @param engine
     */
    public void run(BackEnd engine) {
        backEnd = engine;
        Application.launch();
    }

    /**
     * 
     * @param stage
     */
    @Override
    public void start(final Stage stage) {
        mStage = stage;
        TextInputDialog deckPrompt = new TextInputDialog(); // Create Window
        deckPrompt.setTitle("BlackjackFX"); // Set Window Title
        ((Stage) deckPrompt.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogo)); // Set Window Image
        deckPrompt.setGraphic(new ImageView(new Image(Constants.blackjackLogo, 80, 115, true, true))); // Set Graphic in Window
        deckPrompt.setHeaderText(greetingString); // Set Text in Window
        deckPrompt.getEditor().setPromptText("Card Counting: 4 | Normal Game: 6"); // Set Text in Textbox
        
        Button okButton = (Button) deckPrompt.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setOnAction((event) -> {
            String stringDecks = deckPrompt.getEditor().getText();
            
            try {
                numOfDecks = Integer.parseInt(stringDecks);
                backEnd.createDeck(numOfDecks);
                launchGame();
            } catch (NumberFormatException e) {
                greetingString = "Welcome to BlackjackFX!\nEnter Number of\nDecks as an Integer:";
                start(mStage);
            }
        });
        
        deckPrompt.show();
    }

    /**
     * 
     */
    private void startingConfig() {
        mStage.setTitle("BlackjackFX");
        mStage.getIcons().add(new Image(Constants.blackjackLogo));
        
        Group group = new Group();
        Scene scene = new Scene(group, 1200, 700);
        scene.setFill(Color.DARKGREEN);
        
        Button p1HitButton = new Button("Hit");
        Button p2HitButton = new Button("Hit");
        Button p3HitButton = new Button("Hit");
        Button p4HitButton = new Button("Hit");

        p1HitButton.setLayoutX(120);
        p1HitButton.setLayoutY(600);
        p2HitButton.setLayoutX(420);
        p2HitButton.setLayoutY(600);
        p3HitButton.setLayoutX(720);
        p3HitButton.setLayoutY(600);
        p4HitButton.setLayoutX(1020);
        p4HitButton.setLayoutY(600);
        group.getChildren().addAll(p1HitButton, p2HitButton, p3HitButton, p4HitButton);

        TextField p1WagerBox = new TextField();
        TextField p2WagerBox = new TextField();
        TextField p3WagerBox = new TextField();
        TextField p4WagerBox = new TextField();
        p1WagerBox.setPromptText("Wager $");
        p2WagerBox.setPromptText("Wager $");
        p3WagerBox.setPromptText("Wager $");
        p4WagerBox.setPromptText("Wager $");

        p1WagerBox.setPrefWidth(60);
        p1WagerBox.setLayoutX(120);
        p1WagerBox.setLayoutY(630);
        p2WagerBox.setPrefWidth(60);
        p2WagerBox.setLayoutX(420);
        p2WagerBox.setLayoutY(630);
        p3WagerBox.setPrefWidth(60);
        p3WagerBox.setLayoutX(720);
        p3WagerBox.setLayoutY(630);
        p4WagerBox.setPrefWidth(60);
        p4WagerBox.setLayoutX(1020);
        p4WagerBox.setLayoutY(630);
        group.getChildren().addAll(p1WagerBox, p2WagerBox, p3WagerBox, p4WagerBox);

        
        ImageView shoePile = new ImageView(new Image(Constants.cardBackFilePath, 80, 115, true, true));
        shoePile.setX(1050);
        shoePile.setY(25);
        ImageView discardPile = new ImageView(new Image(Constants.cardBackFilePath, 80, 115, true, true));
        discardPile.setX(65);
        discardPile.setY(25);
        ImageView cutCard = new ImageView(new Image(Constants.cutCardFilePath, 80, 115, true, true));
        cutCard.setX(950);
        cutCard.setY(25);

        group.getChildren().addAll(shoePile, discardPile, cutCard);
        mStage.setScene(scene);
    }

    private void launchGame() {
        startingConfig();
        mStage.show();

        // Four TextBox's for possible player ID's
        // A create new player button (Prompts for name and cash)
    }
}
