import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Class that handles the front end UI for Blackjack
 * 
 * @author Darren Seubert
 */
public class FrontEnd extends Application {
    private static BackEnd backEnd;
    private Stage mStage;
    private String greetingString;
    private int p1ID;
    private int p2ID;
    private int p3ID;
    private int p4ID;

    /**
     * 
     */
    public FrontEnd() {
        p1ID = -1;
        p2ID = -1;
        p3ID = -1;
        p4ID = -1;
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
                int numOfDecks = Integer.parseInt(stringDecks);
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

    /**
     * 
     */
    private void launchGame() {
        mStage.setTitle("BlackjackFX");
        mStage.getIcons().add(new Image(Constants.blackjackLogoFilePath));
        
        Group group = new Group();
        Scene scene = new Scene(group, 1200, 700);
        scene.setFill(Color.DARKGREEN);
        mStage.setScene(scene);
        mStage.setResizable(false);

        setLayoutGrid(group); // TODO REMOVE IN FINAL CODE

        ImageView tableLogo = new ImageView(new Image(Constants.blackjackLogoFilePath, 250, 250, true, true));
        tableLogo.setX(475);
        tableLogo.setY(165);
        group.getChildren().add(tableLogo);

        ImageView shoePile = new ImageView(Constants.backOfCardImage);
        shoePile.setX(Constants.shoePileXPos);
        shoePile.setY(Constants.shoePileYPos);
        ImageView discardPile = new ImageView(Constants.backOfCardImage);
        discardPile.setX(65);
        discardPile.setY(25);
        discardPile.setVisible(false);
        ImageView cutCard = new ImageView(Constants.cutCardImage);
        cutCard.setX(Constants.shoePileXPos);
        cutCard.setY(Constants.shoePileYPos);
        cutCard.setVisible(false);

        Text numOfCardsInShoe = new Text(Integer.toString(backEnd.getDecks().getNumberOfCards()));
        numOfCardsInShoe.setFont(Font.font("Verdana", 13));
        numOfCardsInShoe.setFill(Color.GHOSTWHITE);
        numOfCardsInShoe.setX(1078);
        numOfCardsInShoe.setY(155);
        group.getChildren().addAll(shoePile, discardPile, cutCard, numOfCardsInShoe);

        // TODO Start of Interactive Elements
        Button newAccountButton = new Button("Open New\nAccount");
        newAccountButton.setLayoutX(524);
        newAccountButton.setLayoutY(25);
        newAccountButton.setTextAlignment(TextAlignment.CENTER);
        newAccountButton.setOnAction((event) -> {
            Dialog<Pair<String, Integer>> newAccountPrompt = new Dialog<>();
            newAccountPrompt.setTitle("BlackjackFX");
            ((Stage) newAccountPrompt.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath)); // Set Window Image
            newAccountPrompt.setGraphic(new ImageView(new Image(Constants.blackjackLogoFilePath, 80, 115, true, true))); // Set Graphic in Window
            newAccountPrompt.setHeaderText("Enter Name and Amount of\nCash for the New Account");
            newAccountPrompt.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nameField = new TextField();
            TextField cashField = new TextField();
            nameField.setPromptText("Enter Name for Account");
            cashField.setPromptText("Enter Amount to Deposit");

            grid.add(new Label("Name:"), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("Cash: $"), 0, 1);
            grid.add(cashField, 1, 1);

            newAccountPrompt.getDialogPane().setContent(grid);
            newAccountPrompt.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    try {
                        String nameEntry = nameField.getText().trim();
                        int cashEntry = Integer.parseInt(cashField.getText());
                        if (nameEntry.equals("") || cashEntry <= 0) {
                            throw new IllegalArgumentException();
                        }

                        return new Pair<>(nameEntry, cashEntry);
                    } catch (NumberFormatException e1) {
                        Alert e1Alert = new Alert(AlertType.ERROR);
                        e1Alert.setTitle("BlackjackFX");
                        ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                        e1Alert.setHeaderText("Error: Integer must be Entered for Cash");
                        e1Alert.show();
                    } catch (IllegalArgumentException e2) {
                        Alert e2Alert = new Alert(AlertType.ERROR);
                        e2Alert.setTitle("BlackjackFX");
                        ((Stage) e2Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                        e2Alert.setHeaderText("Error: Name must not be Blank or Cash must be Greater than 0");
                        e2Alert.show();
                    }
                }
                return null;
            });

            Optional<Pair<String, Integer>> result = newAccountPrompt.showAndWait();
            result.ifPresent(nameCashPair -> {
                backEnd.addNewPlayerToGame(nameCashPair.getKey(), nameCashPair.getValue());

                int playerIDNumber = backEnd.getDm().getLargestIDNumber();
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("BlackjackFX");
                ((Stage) successAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                successAlert.setHeaderText("Account Successfully Opened!\n" +
                    "Name: " + backEnd.getDm().playerTable.get(playerIDNumber).getName() + "\n" +
                    "ID Number: " + playerIDNumber + "\n" +
                    "Balance: $" + backEnd.getDm().playerTable.get(playerIDNumber).getCash());
                successAlert.show();
            });
        });

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
            lookupAccountIDPrompt.getEditor().setPromptText("Lookup Name");
            lookupAccountIDPrompt.show();

            Button okButton = (Button) lookupAccountIDPrompt.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setOnAction((okEvent) -> {
                String lookUpName = lookupAccountIDPrompt.getEditor().getText().trim();
                int resultID = backEnd.getDm().lookupPlayerID(lookUpName);
                if (resultID == -1) {
                    Alert personDNEAlert = new Alert(AlertType.ERROR);
                    personDNEAlert.setTitle("BlackjackFX");
                    ((Stage) personDNEAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    personDNEAlert.setHeaderText("Player Does not Exist, Open a New Account");
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

        Button manageCashButton = new Button("Manage Cash\nin Account");
        manageCashButton.setLayoutX(555);
        manageCashButton.setLayoutY(73);
        manageCashButton.setTextAlignment(TextAlignment.CENTER);
        manageCashButton.setOnAction((event) -> {
            Dialog<Pair<Integer, Integer>> manageCashPrompt = new Dialog<>();
            manageCashPrompt.setTitle("BlackjackFX");
            ((Stage) manageCashPrompt.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath)); // Set Window Image
            manageCashPrompt.setGraphic(new ImageView(new Image(Constants.blackjackLogoFilePath, 80, 115, true, true))); // Set Graphic in Window
            manageCashPrompt.setHeaderText("Enter ID Number and Amount of\nCash to Add or Subtract from Account");
            manageCashPrompt.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField IDField = new TextField();
            TextField cashField = new TextField();
            IDField.setPromptText("Enter ID Number");
            cashField.setPromptText("+ Deposit | - Withdraw");

            grid.add(new Label("Account ID:"), 0, 0);
            grid.add(IDField, 1, 0);
            grid.add(new Label("Cash: $"), 0, 1);
            grid.add(cashField, 1, 1);

            manageCashPrompt.getDialogPane().setContent(grid);
            manageCashPrompt.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    try {
                        int IDEntry = Integer.parseInt(IDField.getText().trim());
                        int cashEntry = Integer.parseInt(cashField.getText().trim());
                        if (IDEntry <= 0 || cashEntry == 0) {
                            throw new IllegalArgumentException();
                        }

                        return new Pair<>(IDEntry, cashEntry);
                    } catch (NumberFormatException e1) {
                        Alert e1Alert = new Alert(AlertType.ERROR);
                        e1Alert.setTitle("BlackjackFX");
                        ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                        e1Alert.setHeaderText("Error: Integer must be Entered for Both Fields");
                        e1Alert.show();
                    } catch (IllegalArgumentException e2) {
                        Alert e2Alert = new Alert(AlertType.ERROR);
                        e2Alert.setTitle("BlackjackFX");
                        ((Stage) e2Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                        e2Alert.setHeaderText("Error: ID must be Postive and Cash cannot be 0");
                        e2Alert.show();
                    }
                }
                return null;
            });

            Optional<Pair<Integer, Integer>> result = manageCashPrompt.showAndWait();
            result.ifPresent(IDCashPair -> {
                int IDEntry = IDCashPair.getKey();
                int cashEntry = IDCashPair.getValue();

                if (backEnd.addOrSubtractCashToPlayer(IDEntry, cashEntry)) {
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("BlackjackFX");
                    ((Stage) successAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    successAlert.setHeaderText("Transaction Complete!\n" +
                        "Name: " + backEnd.getDm().playerTable.get(IDEntry).getName() + "\n" +
                        "ID Number: " + backEnd.getDm().playerTable.get(IDEntry).getIDNumber() + "\n" +
                        "Balance: $" + backEnd.getDm().playerTable.get(IDEntry).getCash());
                    successAlert.show();
                } else {
                    Alert failAlert = new Alert(AlertType.ERROR);
                    failAlert.setTitle("BlackjackFX");
                    ((Stage) failAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    if (backEnd.checkIfPlayerExists(IDEntry)) {
                        failAlert.setHeaderText("Error: Insufficient Funds\n" +
                        "Name: " + backEnd.getDm().playerTable.get(IDEntry).getName() + "\n" +
                        "ID Number: " + backEnd.getDm().playerTable.get(IDEntry).getIDNumber() + "\n" +
                        "Balance: $" + backEnd.getDm().playerTable.get(IDEntry).getCash());
                    } else {
                        failAlert.setHeaderText("Error: Account Does not Exist");
                    }
                    failAlert.show();
                }
            });
        });
        group.getChildren().addAll(newAccountButton, lookupAccountIDButton, manageCashButton);

        // TODO Start of player elements
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
        group.getChildren().addAll(p1IDField, p2IDField, p3IDField, p4IDField);

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
        group.getChildren().addAll(p1SubmitButton, p2SubmitButton, p3SubmitButton, p4SubmitButton);

        TextField p1WagerField = new TextField();
        p1WagerField.setPromptText("Wager $");
        p1WagerField.setPrefWidth(70);
        p1WagerField.setLayoutX(115);
        p1WagerField.setLayoutY(570);
        p1WagerField.setVisible(false);
        TextField p2WagerField = new TextField();
        p2WagerField.setPromptText("Wager $");
        p2WagerField.setPrefWidth(70);
        p2WagerField.setLayoutX(415);
        p2WagerField.setLayoutY(570);
        p2WagerField.setVisible(false);
        TextField p3WagerField = new TextField();
        p3WagerField.setPromptText("Wager $");
        p3WagerField.setPrefWidth(70);
        p3WagerField.setLayoutX(715);
        p3WagerField.setLayoutY(570);
        p3WagerField.setVisible(false);
        TextField p4WagerField = new TextField();
        p4WagerField.setPromptText("Wager $");
        p4WagerField.setPrefWidth(70);
        p4WagerField.setLayoutX(1015);
        p4WagerField.setLayoutY(570);
        p4WagerField.setVisible(false);
        group.getChildren().addAll(p1WagerField, p2WagerField, p3WagerField, p4WagerField);

        Button p1LeaveButton = new Button("Leave");
        p1LeaveButton.setTextAlignment(TextAlignment.CENTER);
        p1LeaveButton.setLayoutX(115);
        p1LeaveButton.setLayoutY(605);
        p1LeaveButton.setPrefWidth(70);
        p1LeaveButton.setVisible(false);
        Button p2LeaveButton = new Button("Leave");
        p2LeaveButton.setTextAlignment(TextAlignment.CENTER);
        p2LeaveButton.setLayoutX(415);
        p2LeaveButton.setLayoutY(605);
        p2LeaveButton.setPrefWidth(70);
        p2LeaveButton.setVisible(false);
        Button p3LeaveButton = new Button("Leave");
        p3LeaveButton.setTextAlignment(TextAlignment.CENTER);
        p3LeaveButton.setLayoutX(715);
        p3LeaveButton.setLayoutY(605);
        p3LeaveButton.setPrefWidth(70);
        p3LeaveButton.setVisible(false);
        Button p4LeaveButton = new Button("Leave");
        p4LeaveButton.setTextAlignment(TextAlignment.CENTER);
        p4LeaveButton.setLayoutX(1015);
        p4LeaveButton.setLayoutY(605);
        p4LeaveButton.setPrefWidth(70);
        p4LeaveButton.setVisible(false);
        group.getChildren().addAll(p1LeaveButton, p2LeaveButton, p3LeaveButton, p4LeaveButton);

        Text p1IDAndNameText = new Text();
        p1IDAndNameText.setFont(Font.font("Verdana", 12));
        p1IDAndNameText.setFill(Color.GHOSTWHITE);
        p1IDAndNameText.setX(5);
        p1IDAndNameText.setY(666);
        p1IDAndNameText.setVisible(false);
        Text p2IDAndNameText = new Text();
        p2IDAndNameText.setFont(Font.font("Verdana", 12));
        p2IDAndNameText.setFill(Color.GHOSTWHITE);
        p2IDAndNameText.setX(305);
        p2IDAndNameText.setY(666);
        p2IDAndNameText.setVisible(false);
        Text p3IDAndNameText = new Text();
        p3IDAndNameText.setFont(Font.font("Verdana", 12));
        p3IDAndNameText.setFill(Color.GHOSTWHITE);
        p3IDAndNameText.setX(605);
        p3IDAndNameText.setY(666);
        p3IDAndNameText.setVisible(false);
        Text p4IDAndNameText = new Text();
        p4IDAndNameText.setFont(Font.font("Verdana", 12));
        p4IDAndNameText.setFill(Color.GHOSTWHITE);
        p4IDAndNameText.setX(905);
        p4IDAndNameText.setY(666);
        p4IDAndNameText.setVisible(false);
        group.getChildren().addAll(p1IDAndNameText, p2IDAndNameText, p3IDAndNameText, p4IDAndNameText);

        Text p1CashText = new Text();
        p1CashText.setFont(Font.font("Verdana", 12));
        p1CashText.setFill(Color.GHOSTWHITE);
        p1CashText.setX(5);
        p1CashText.setY(695);
        p1CashText.setVisible(false);
        Text p2CashText = new Text();
        p2CashText.setFont(Font.font("Verdana", 12));
        p2CashText.setFill(Color.GHOSTWHITE);
        p2CashText.setX(305);
        p2CashText.setY(695);
        p2CashText.setVisible(false);
        Text p3CashText = new Text();
        p3CashText.setFont(Font.font("Verdana", 12));
        p3CashText.setFill(Color.GHOSTWHITE);
        p3CashText.setX(605);
        p3CashText.setY(695);
        p3CashText.setVisible(false);
        Text p4CashText = new Text();
        p4CashText.setFont(Font.font("Verdana", 12));
        p4CashText.setFill(Color.GHOSTWHITE);
        p4CashText.setX(905);
        p4CashText.setY(695);
        p4CashText.setVisible(false);
        group.getChildren().addAll(p1CashText, p2CashText, p3CashText, p4CashText);

        Button dealButton = new Button("DEAL");
        dealButton.setFont(new Font(20));
        dealButton.setPrefWidth(74);
        dealButton.setPrefHeight(40);
        dealButton.setLayoutX(563);
        dealButton.setLayoutY(580);
        dealButton.setTextAlignment(TextAlignment.CENTER);
        dealButton.setVisible(false);
        group.getChildren().add(dealButton);

        Text p1WagerText = new Text();
        p1WagerText.setFont(Font.font("Verdana", 12));
        p1WagerText.setFill(Color.GHOSTWHITE);
        p1WagerText.setX(155);
        p1WagerText.setY(695);
        p1WagerText.setVisible(false);
        Text p2WagerText = new Text();
        p2WagerText.setFont(Font.font("Verdana", 12));
        p2WagerText.setFill(Color.GHOSTWHITE);
        p2WagerText.setX(455);
        p2WagerText.setY(695);
        p2WagerText.setVisible(false);
        Text p3WagerText = new Text();
        p3WagerText.setFont(Font.font("Verdana", 12));
        p3WagerText.setFill(Color.GHOSTWHITE);
        p3WagerText.setX(755);
        p3WagerText.setY(695);
        p3WagerText.setVisible(false);
        Text p4WagerText = new Text();
        p4WagerText.setFont(Font.font("Verdana", 12));
        p4WagerText.setFill(Color.GHOSTWHITE);
        p4WagerText.setX(1055);
        p4WagerText.setY(695);
        p4WagerText.setVisible(false);
        group.getChildren().addAll(p1WagerText, p2WagerText, p3WagerText, p4WagerText);

        // Button p1HitButton = new Button("Hit");
        // p1HitButton.setLayoutX(100);
        // p1HitButton.setLayoutY(570);
        // p1HitButton.setVisible(false);
        // Button p2HitButton = new Button("Hit");
        // p2HitButton.setLayoutX(400);
        // p2HitButton.setLayoutY(570);
        // p2HitButton.setVisible(false);
        // Button p3HitButton = new Button("Hit");
        // p3HitButton.setLayoutX(700);
        // p3HitButton.setLayoutY(570);
        // p3HitButton.setVisible(false);
        // Button p4HitButton = new Button("Hit");
        // p4HitButton.setLayoutX(1000);
        // p4HitButton.setLayoutY(570);
        // p4HitButton.setVisible(false);
        // group.getChildren().addAll(p1HitButton, p2HitButton, p3HitButton, p4HitButton);
        
        // TODO Start of Button Actions
        p1SubmitButton.setOnAction((event) -> {
            try {
                int IDEntry = Integer.parseInt(p1IDField.getText().trim());
                if (IDEntry <= 0) {
                    throw new IllegalArgumentException();
                }

                if (!backEnd.checkIfPlayerExists(IDEntry)) {
                    throw new NoSuchElementException();
                }

                p1ID = IDEntry;
                p1IDField.setVisible(false);
                p1SubmitButton.setVisible(false);

                p1WagerField.clear();
                p1WagerField.setVisible(true);
                p1LeaveButton.setVisible(true);
                p1IDAndNameText.setText("ID: " + IDEntry + "\nName: " + backEnd.getDm().playerTable.get(IDEntry).getName());
                p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(IDEntry).getCash());
                p1IDAndNameText.setVisible(true);
                p1CashText.setVisible(true);
                dealButton.setVisible(true);
            } catch (NumberFormatException e1) {
                Alert e1Alert = new Alert(AlertType.ERROR);
                e1Alert.setTitle("BlackjackFX");
                ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e1Alert.setHeaderText("Error: ID Number must be a Integer");
                e1Alert.show();
            } catch (IllegalArgumentException e2) {
                Alert e2Alert = new Alert(AlertType.ERROR);
                e2Alert.setTitle("BlackjackFX");
                ((Stage) e2Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e2Alert.setHeaderText("Error: ID Number must be Greater than 0");
                e2Alert.show();
            } catch (NoSuchElementException e3) {
                Alert e3Alert = new Alert(AlertType.ERROR);
                e3Alert.setTitle("BlackjackFX");
                ((Stage) e3Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e3Alert.setHeaderText("Error: Account Does not Exist, Open a New Account");
                e3Alert.show();
            } finally {
                p1IDField.clear();
                p1IDField.requestFocus();
            }
        });
        p2SubmitButton.setOnAction((event) -> {
            try {
                int IDEntry = Integer.parseInt(p2IDField.getText().trim());
                if (IDEntry <= 0) {
                    throw new IllegalArgumentException();
                }

                if (!backEnd.checkIfPlayerExists(IDEntry)) {
                    throw new NoSuchElementException();
                }

                p2ID = IDEntry;
                p2IDField.setVisible(false);
                p2SubmitButton.setVisible(false);

                p2WagerField.clear();
                p2WagerField.setVisible(true);
                p2LeaveButton.setVisible(true);
                p2IDAndNameText.setText("ID: " + IDEntry + "\nName: " + backEnd.getDm().playerTable.get(IDEntry).getName());
                p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(IDEntry).getCash());
                p2IDAndNameText.setVisible(true);
                p2CashText.setVisible(true);
                dealButton.setVisible(true);
            } catch (NumberFormatException e1) {
                Alert e1Alert = new Alert(AlertType.ERROR);
                e1Alert.setTitle("BlackjackFX");
                ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e1Alert.setHeaderText("Error: ID Number must be a Integer");
                e1Alert.show();
            } catch (IllegalArgumentException e2) {
                Alert e2Alert = new Alert(AlertType.ERROR);
                e2Alert.setTitle("BlackjackFX");
                ((Stage) e2Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e2Alert.setHeaderText("Error: ID Number must be Greater than 0");
                e2Alert.show();
            } catch (NoSuchElementException e3) {
                Alert e3Alert = new Alert(AlertType.ERROR);
                e3Alert.setTitle("BlackjackFX");
                ((Stage) e3Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e3Alert.setHeaderText("Error: Account Does not Exist, Open a New Account");
                e3Alert.show();
            } finally {
                p2IDField.clear();
                p2IDField.requestFocus();
            }
        });
        p3SubmitButton.setOnAction((event) -> {
            try {
                int IDEntry = Integer.parseInt(p3IDField.getText().trim());
                if (IDEntry <= 0) {
                    throw new IllegalArgumentException();
                }

                if (!backEnd.checkIfPlayerExists(IDEntry)) {
                    throw new NoSuchElementException();
                }

                p3ID = IDEntry;
                p3IDField.setVisible(false);
                p3SubmitButton.setVisible(false);

                p3WagerField.clear();
                p3WagerField.setVisible(true);
                p3LeaveButton.setVisible(true);
                p3IDAndNameText.setText("ID: " + IDEntry + "\nName: " + backEnd.getDm().playerTable.get(IDEntry).getName());
                p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(IDEntry).getCash());
                p3IDAndNameText.setVisible(true);
                p3CashText.setVisible(true);
                dealButton.setVisible(true);
            } catch (NumberFormatException e1) {
                Alert e1Alert = new Alert(AlertType.ERROR);
                e1Alert.setTitle("BlackjackFX");
                ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e1Alert.setHeaderText("Error: ID Number must be a Integer");
                e1Alert.show();
            } catch (IllegalArgumentException e2) {
                Alert e2Alert = new Alert(AlertType.ERROR);
                e2Alert.setTitle("BlackjackFX");
                ((Stage) e2Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e2Alert.setHeaderText("Error: ID Number must be Greater than 0");
                e2Alert.show();
            } catch (NoSuchElementException e3) {
                Alert e3Alert = new Alert(AlertType.ERROR);
                e3Alert.setTitle("BlackjackFX");
                ((Stage) e3Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e3Alert.setHeaderText("Error: Account Does not Exist, Open a New Account");
                e3Alert.show();
            } finally {
                p3IDField.clear();
                p3IDField.requestFocus();
            }
        });
        p4SubmitButton.setOnAction((event) -> {
            try {
                int IDEntry = Integer.parseInt(p4IDField.getText().trim());
                if (IDEntry <= 0) {
                    throw new IllegalArgumentException();
                }

                if (!backEnd.checkIfPlayerExists(IDEntry)) {
                    throw new NoSuchElementException();
                }

                p4ID = IDEntry;
                p4IDField.setVisible(false);
                p4SubmitButton.setVisible(false);

                p4WagerField.clear();
                p4WagerField.setVisible(true);
                p4LeaveButton.setVisible(true);
                p4IDAndNameText.setText("ID: " + IDEntry + "\nName: " + backEnd.getDm().playerTable.get(IDEntry).getName());
                p4CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(IDEntry).getCash());
                p4IDAndNameText.setVisible(true);
                p4CashText.setVisible(true);
                dealButton.setVisible(true);
            } catch (NumberFormatException e1) {
                Alert e1Alert = new Alert(AlertType.ERROR);
                e1Alert.setTitle("BlackjackFX");
                ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e1Alert.setHeaderText("Error: ID Number must be a Integer");
                e1Alert.show();
            } catch (IllegalArgumentException e2) {
                Alert e2Alert = new Alert(AlertType.ERROR);
                e2Alert.setTitle("BlackjackFX");
                ((Stage) e2Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e2Alert.setHeaderText("Error: ID Number must be Greater than 0");
                e2Alert.show();
            } catch (NoSuchElementException e3) {
                Alert e3Alert = new Alert(AlertType.ERROR);
                e3Alert.setTitle("BlackjackFX");
                ((Stage) e3Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e3Alert.setHeaderText("Error: Account Does not Exist, Open a New Account");
                e3Alert.show();
            } finally {
                p4IDField.clear();
                p4IDField.requestFocus();
            }
        });

        p1LeaveButton.setOnAction((event) -> {
            p1ID = -1;
            p1WagerField.setVisible(false);
            p1LeaveButton.setVisible(false);
            p1IDAndNameText.setVisible(false);
            p1CashText.setVisible(false);
            if (p1ID == -1 && p2ID == -1 && p3ID == -1 && p4ID == -1) {
                dealButton.setVisible(false);
            }

            p1IDField.clear();
            p1IDField.setVisible(true);
            p1SubmitButton.setVisible(true);
        });
        p2LeaveButton.setOnAction((event) -> {
            p2ID = -1;
            p2WagerField.setVisible(false);
            p2LeaveButton.setVisible(false);
            p2IDAndNameText.setVisible(false);
            p2CashText.setVisible(false);
            if (p1ID == -1 && p2ID == -1 && p3ID == -1 && p4ID == -1) {
                dealButton.setVisible(false);
            }

            p2IDField.clear();
            p2IDField.setVisible(true);
            p2SubmitButton.setVisible(true);
        });
        p3LeaveButton.setOnAction((event) -> {
            p3ID = -1;
            p3WagerField.setVisible(false);
            p3LeaveButton.setVisible(false);
            p3IDAndNameText.setVisible(false);
            p3CashText.setVisible(false);
            if (p1ID == -1 && p2ID == -1 && p3ID == -1 && p4ID == -1) {
                dealButton.setVisible(false);
            }

            p3IDField.clear();
            p3IDField.setVisible(true);
            p3SubmitButton.setVisible(true);
        });
        p4LeaveButton.setOnAction((event) -> {
            p4ID = -1;
            p4WagerField.setVisible(false);
            p4LeaveButton.setVisible(false);
            p4IDAndNameText.setVisible(false);
            p4CashText.setVisible(false);
            if (p1ID == -1 && p2ID == -1 && p3ID == -1 && p4ID == -1) {
                dealButton.setVisible(false);
            }

            p4IDField.clear();
            p4IDField.setVisible(true);
            p4SubmitButton.setVisible(true);
        });

        dealButton.setOnAction((event) -> {
            try {
                boolean p1InUse = false;
                boolean p2InUse = false;
                boolean p3InUse = false;
                boolean p4InUse = false;
                int p1WagerEntry = 0;
                int p2WagerEntry = 0;
                int p3WagerEntry = 0;
                int p4WagerEntry = 0;

                // Checks for Valid Input
                if (p1WagerField.isVisible()) {
                    try {
                        p1WagerEntry = Integer.parseInt(p1WagerField.getText().trim());
                    } catch (NumberFormatException e) {
                        p1WagerField.clear();
                        p1WagerField.requestFocus();
                        throw new NumberFormatException(backEnd.getDm().playerTable.get(p1ID).getName());
                    }
                    if (p1WagerEntry <= 0) {
                        p1WagerField.clear();
                        p1WagerField.requestFocus();
                        throw new IllegalArgumentException(backEnd.getDm().playerTable.get(p1ID).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(p1ID, -p1WagerEntry)) {
                        p1WagerField.clear();
                        p1WagerField.requestFocus();
                        throw new ArithmeticException(backEnd.getDm().playerTable.get(p1ID).getName());
                    }

                    p1InUse = true;
                }
                if (p2WagerField.isVisible()) {
                    try {
                        p2WagerEntry = Integer.parseInt(p2WagerField.getText().trim());
                    } catch (NumberFormatException e) {
                        p2WagerField.clear();
                        p2WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        throw new NumberFormatException(backEnd.getDm().playerTable.get(p2ID).getName());
                    }
                    if (p2WagerEntry <= 0) {
                        p2WagerField.clear();
                        p2WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        throw new IllegalArgumentException(backEnd.getDm().playerTable.get(p2ID).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(p2ID, -p2WagerEntry)) {
                        p2WagerField.clear();
                        p2WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        throw new ArithmeticException(backEnd.getDm().playerTable.get(p2ID).getName());
                    }

                    p2InUse = true;
                }
                if (p3WagerField.isVisible()) {
                    try {
                        p3WagerEntry = Integer.parseInt(p3WagerField.getText().trim());
                    } catch (NumberFormatException e) {
                        p3WagerField.clear();
                        p3WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        throw new NumberFormatException(backEnd.getDm().playerTable.get(p3ID).getName());
                    }
                    if (p3WagerEntry <= 0) {
                        p3WagerField.clear();
                        p3WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        throw new IllegalArgumentException(backEnd.getDm().playerTable.get(p3ID).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(p3ID, -p3WagerEntry)) {
                        p3WagerField.clear();
                        p3WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        throw new ArithmeticException(backEnd.getDm().playerTable.get(p3ID).getName());
                    }

                    p3InUse = true;
                }
                if (p4WagerField.isVisible()) {
                    try {
                        p4WagerEntry = Integer.parseInt(p4WagerField.getText().trim());
                    } catch (NumberFormatException e) {
                        p4WagerField.clear();
                        p4WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p3ID, p3WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p3ID).getCash());
                        throw new NumberFormatException(backEnd.getDm().playerTable.get(p4ID).getName());
                    }
                    if (p4WagerEntry <= 0) {
                        p4WagerField.clear();
                        p4WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p3ID, p3WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p3ID).getCash());
                        throw new IllegalArgumentException(backEnd.getDm().playerTable.get(p4ID).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(p4ID, -p4WagerEntry)) {
                        p4WagerField.clear();
                        p4WagerField.requestFocus();
                        backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry);
                        backEnd.addOrSubtractCashToPlayer(p3ID, p3WagerEntry);
                        p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p3ID).getCash());
                        throw new ArithmeticException(backEnd.getDm().playerTable.get(p4ID).getName());
                    }

                    p4InUse = true;
                }
                dealButton.setVisible(false);

                // Blackjack Time!
                newAccountButton.setDisable(true);
                lookupAccountIDButton.setDisable(true);
                manageCashButton.setDisable(true);
                p1IDField.setVisible(false);
                p1SubmitButton.setVisible(false);
                p2IDField.setVisible(false);
                p2SubmitButton.setVisible(false);
                p3IDField.setVisible(false);
                p3SubmitButton.setVisible(false);
                p4IDField.setVisible(false);
                p4SubmitButton.setVisible(false);

                if (p1InUse) {
                    p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                    p1WagerField.setVisible(false);
                    p1LeaveButton.setVisible(false);
                    p1WagerText.setText("Wager: $" + p1WagerEntry);
                    p1WagerText.setVisible(true);
                }
                if (p2InUse) {
                    p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                    p2WagerField.setVisible(false);
                    p2LeaveButton.setVisible(false);
                    p2WagerText.setText("Wager: $" + p2WagerEntry);
                    p2WagerText.setVisible(true);
                }
                if (p3InUse) {
                    p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p3ID).getCash());
                    p3WagerField.setVisible(false);
                    p3LeaveButton.setVisible(false);
                    p3WagerText.setText("Wager: $" + p3WagerEntry);
                    p3WagerText.setVisible(true);
                }
                if (p4InUse) {
                    p4CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p4ID).getCash());
                    p4WagerField.setVisible(false);
                    p4LeaveButton.setVisible(false);
                    p4WagerText.setText("Wager: $" + p4WagerEntry);
                    p4WagerText.setVisible(true);
                }
            } catch (NumberFormatException e1) {
                Alert e1Alert = new Alert(AlertType.ERROR);
                e1Alert.setTitle("BlackjackFX");
                ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e1Alert.setHeaderText("Error: " + e1.getMessage() + "'s Wager must be a Integer");
                e1Alert.show();
            } catch (IllegalArgumentException e2) {
                Alert e2Alert = new Alert(AlertType.ERROR);
                e2Alert.setTitle("BlackjackFX");
                ((Stage) e2Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e2Alert.setHeaderText("Error: " + e2.getMessage() +"'s Wager must be Greater than 0");
                e2Alert.show();
            } catch (ArithmeticException e3){
                Alert e3Alert = new Alert(AlertType.ERROR);
                e3Alert.setTitle("BlackjackFX");
                ((Stage) e3Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e3Alert.setHeaderText("Error: " + e3.getMessage() + " has Insufficient Funds");
                e3Alert.show();
            }
        });

        mStage.show();
    }
}
