import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
     * Method that prompts user to enter number of decks, then launches rest of the game
     * 
     * @param stage
     */
    @Override
    public void start(final Stage stage) {
        mStage = stage;
        TextInputDialog deckPrompt = new TextInputDialog(); // Create Window
        deckPrompt.setTitle("BlackjackFX"); // Set Window Title
        ((Stage) deckPrompt.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath)); // Set Window Image
        deckPrompt.setGraphic(new ImageView(new Image(Constants.blackjackLogoFilePath, 80, 115, true, true))); // Set Graphic in Window
        deckPrompt.setHeaderText(greetingString); // Set Text in Window
        deckPrompt.getEditor().setPromptText("Card Counting: 4 | Normal Game: 6"); // Set Text in Textbox
        
        Button okButton = (Button) deckPrompt.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setOnAction((event) -> {
            String stringDecks = deckPrompt.getEditor().getText().trim();
            
            try {
                numOfDecks = Integer.parseInt(stringDecks);
                if (numOfDecks < 1 || numOfDecks > 100) {
                    throw new IllegalArgumentException();
                }

                backEnd.createDeck(numOfDecks);
                launchGame();
            } catch (NumberFormatException e1) {
                greetingString = "Welcome to BlackjackFX!\nEnter Number of Decks\nas a Positive Integer";
                start(mStage);
            } catch (IllegalArgumentException e2){
                greetingString = "Welcome to BlackjackFX!\nEnter Number of Decks\nBetween 1-100:";
                start(mStage);
            }
        });
        
        deckPrompt.show();

        //backEnd.createDeck(-1); // REMOVE THIS WHEN WANT TO TEST FULL PROJECT
        //launchGame(); // REMOVE THIS WHEN WANT TO TEST FULL PROJECT
    }

    private void setLayoutGrid(Group group) { // TODO REMOVE FOR FINAL PROJECT
        Rectangle middleLine = new Rectangle();
        middleLine.setWidth(1);
        middleLine.setHeight(700);
        middleLine.setLayoutX(600);
        middleLine.setLayoutY(0);
        middleLine.setFill(Color.BLACK);

        Rectangle horLine = new Rectangle();
        horLine.setWidth(1200);
        horLine.setHeight(1);
        horLine.setLayoutX(0);
        horLine.setLayoutY(500);
        horLine.setFill(Color.BLACK);

        Rectangle line1 = new Rectangle();
        line1.setWidth(1);
        line1.setHeight(200);
        line1.setLayoutX(300);
        line1.setLayoutY(500);
        line1.setFill(Color.BLACK);

        Rectangle line3 = new Rectangle();
        line3.setWidth(1);
        line3.setHeight(200);
        line3.setLayoutX(900);
        line3.setLayoutY(500);
        line3.setFill(Color.BLACK);

        Rectangle subLine1 = new Rectangle();
        subLine1.setWidth(1);
        subLine1.setHeight(180);
        subLine1.setLayoutX(150);
        subLine1.setLayoutY(510);
        subLine1.setFill(Color.RED);

        Rectangle subLine2 = new Rectangle();
        subLine2.setWidth(1);
        subLine2.setHeight(180);
        subLine2.setLayoutX(450);
        subLine2.setLayoutY(510);
        subLine2.setFill(Color.RED);

        Rectangle subLine3 = new Rectangle();
        subLine3.setWidth(1);
        subLine3.setHeight(180);
        subLine3.setLayoutX(750);
        subLine3.setLayoutY(510);
        subLine3.setFill(Color.RED);

        Rectangle subLine4 = new Rectangle();
        subLine4.setWidth(1);
        subLine4.setHeight(180);
        subLine4.setLayoutX(1050);
        subLine4.setLayoutY(510);
        subLine4.setFill(Color.RED);

        Rectangle horLine2 = new Rectangle();
        horLine2.setWidth(1200);
        horLine2.setHeight(1);
        horLine2.setLayoutX(0);
        horLine2.setLayoutY(600);
        horLine2.setFill(Color.RED);

        group.getChildren().addAll(middleLine, horLine, line1, line3, subLine1, subLine2, subLine3, subLine4, horLine2);
    }

    private void launchGame() {
        mStage.setTitle("BlackjackFX");
        mStage.getIcons().add(new Image(Constants.blackjackLogoFilePath));
        
        Group group = new Group();
        Scene scene = new Scene(group, 1200, 700);
        scene.setFill(Color.DARKGREEN);
        mStage.setScene(scene);

        setLayoutGrid(group); // TO REMOVE IN FINAL CODE

        ImageView tableLogo = new ImageView(new Image(Constants.blackjackLogoFilePath, 250, 250, true, true));
        tableLogo.setX(475);
        tableLogo.setY(165);
        group.getChildren().add(tableLogo);

        ImageView shoePile = new ImageView(Constants.backOfCardImage);
        shoePile.setX(1050);
        shoePile.setY(25);
        Text numOfCardsInShoe = new Text(Integer.toString(backEnd.getDecks().getCardList().size()));
        numOfCardsInShoe.setFont(Font.font("Verdana", 13));
        numOfCardsInShoe.setFill(Color.GHOSTWHITE);
        numOfCardsInShoe.setX(1078);
        numOfCardsInShoe.setY(155);
        group.getChildren().addAll(shoePile, numOfCardsInShoe);

        Button newAccountButton = new Button("Open New\nAccount");
        newAccountButton.setLayoutX(524);
        newAccountButton.setLayoutY(25);
        newAccountButton.setTextAlignment(TextAlignment.CENTER);

        Button lookupAccountIDButton = new Button("Lookup\nAccount ID");
        lookupAccountIDButton.setLayoutX(605);
        lookupAccountIDButton.setLayoutY(25);
        lookupAccountIDButton.setTextAlignment(TextAlignment.CENTER);
        lookupAccountIDButton.setOnAction((event) -> {
            TextInputDialog lookupAccountIDPrompt = new TextInputDialog();
            lookupAccountIDPrompt.setTitle("BlackjackFX"); // Set Window Title
            ((Stage) lookupAccountIDPrompt.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath)); // Set Window Image
            lookupAccountIDPrompt.setGraphic(new ImageView(new Image(Constants.blackjackLogoFilePath, 80, 115, true, true))); // Set Graphic in Window
            lookupAccountIDPrompt.setHeaderText("Type in Name\nto Lookup"); // Set Text in Window
            lookupAccountIDPrompt.show();

            Button okButton = (Button) lookupAccountIDPrompt.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setOnAction((okEvent) -> {
                String lookUpName = lookupAccountIDPrompt.getEditor().getText().trim();
                int resultID = backEnd.getDm().getPlayerID(lookUpName);
                if (resultID == -1) {
                    Alert personDNEAlert = new Alert(AlertType.ERROR);
                    personDNEAlert.setTitle("BlackjackFX");
                    ((Stage) personDNEAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    personDNEAlert.setHeaderText("Player does not Exist, Create a New Account");
                    personDNEAlert.show();
                } else {
                    Alert personIDAlert = new Alert(AlertType.INFORMATION);
                    personIDAlert.setTitle("BlackjackFX");
                    ((Stage) personIDAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    personIDAlert.setHeaderText(lookUpName + "'s ID Number is: " + resultID);
                    personIDAlert.show();
                }
            });
        });

        Button addCashButton = new Button("Add Cash\nto Account");
        addCashButton.setLayoutX(565);
        addCashButton.setLayoutY(73);
        addCashButton.setTextAlignment(TextAlignment.CENTER);

        Button dealButton = new Button("DEAL");
        dealButton.setFont(new Font(20));
        dealButton.setPrefWidth(74);
        dealButton.setPrefHeight(40);
        dealButton.setLayoutX(563);
        dealButton.setLayoutY(580);
        dealButton.setTextAlignment(TextAlignment.CENTER);
        group.getChildren().addAll(newAccountButton, lookupAccountIDButton, addCashButton, dealButton);

        TextField p1IDField = new TextField();
        p1IDField.setPromptText("Enter ID #");
        p1IDField.setLayoutX(115);
        p1IDField.setLayoutY(570);
        p1IDField.setPrefWidth(70);
        TextField p2IDField = new TextField();
        p2IDField.setPromptText("Enter ID #");
        p2IDField.setLayoutX(415);
        p2IDField.setLayoutY(570);
        p2IDField.setPrefWidth(70);
        TextField p3IDField = new TextField();
        p3IDField.setPromptText("Enter ID #");
        p3IDField.setLayoutX(715);
        p3IDField.setLayoutY(570);
        p3IDField.setPrefWidth(70);
        TextField p4IDField = new TextField();
        p4IDField.setPromptText("Enter ID #");
        p4IDField.setLayoutX(1015);
        p4IDField.setLayoutY(570);
        p4IDField.setPrefWidth(70);
        TextField[] iDTextFields = {p1IDField, p2IDField, p3IDField, p4IDField};
        for (TextField textField : iDTextFields) {
            group.getChildren().add(textField);
        }

        Button p1SubmitButton = new Button("Submit");
        p1SubmitButton.setTextAlignment(TextAlignment.CENTER);
        p1SubmitButton.setLayoutX(115);
        p1SubmitButton.setLayoutY(605);
        p1SubmitButton.setPrefWidth(70);
        Button p2SubmitButton = new Button("Submit");
        p2SubmitButton.setTextAlignment(TextAlignment.CENTER);
        p2SubmitButton.setLayoutX(415);
        p2SubmitButton.setLayoutY(605);
        p2SubmitButton.setPrefWidth(70);
        Button p3SubmitButton = new Button("Submit");
        p3SubmitButton.setTextAlignment(TextAlignment.CENTER);
        p3SubmitButton.setLayoutX(715);
        p3SubmitButton.setLayoutY(605);
        p3SubmitButton.setPrefWidth(70);
        Button p4SubmitButton = new Button("Submit");
        p4SubmitButton.setTextAlignment(TextAlignment.CENTER);
        p4SubmitButton.setLayoutX(1015);
        p4SubmitButton.setLayoutY(605);
        p4SubmitButton.setPrefWidth(70);
        Button[] submitButtons = {p1SubmitButton, p2SubmitButton, p3SubmitButton, p4SubmitButton};
        for (Button button : submitButtons) {
            group.getChildren().add(button);
        }

        /*Button p1HitButton = new Button("Hit");
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
        
        ImageView discardPile = new ImageView(Constants.backOfCardImage);
        discardPile.setX(65);
        discardPile.setY(25);
        ImageView cutCard = new ImageView(Constants.cutCardImage);
        cutCard.setX(950);
        cutCard.setY(25);*/

        mStage.show();

        // Four TextBox's for possible player ID's
        // A create new player button (Prompts for name and cash)
    }
}
