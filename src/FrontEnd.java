import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
    private boolean p1InUse;
    private boolean p2InUse;
    private boolean p3InUse;
    private boolean p4InUse;

    /**
     * 
     */
    public FrontEnd() {
        p1ID = -1;
        p2ID = -1;
        p3ID = -1;
        p4ID = -1;
        p1InUse = false;
        p2InUse = false;
        p3InUse = false;
        p4InUse = false;
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

    private void setLayoutGrid(Group group) { // TODO EDIT FOR FINAL PROJECT
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
     * Method that runs the main UI of Blackjack
     */
    private void launchGame() {
        mStage.setTitle("BlackjackFX");
        mStage.getIcons().add(new Image(Constants.blackjackLogoFilePath));
        
        Group group = new Group();
        Scene scene = new Scene(group, 1200, 700);
        scene.setFill(Color.DARKGREEN);
        mStage.setScene(scene);
        mStage.setResizable(false);

        setLayoutGrid(group); // TODO EDIT FOR FINAL PROJECT

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

        // Start of Interactive Elements
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

        // Start of player elements
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

        Button p1YesButton = new Button("Yes");
        p1YesButton.setTextAlignment(TextAlignment.CENTER);
        p1YesButton.setLayoutX(90);
        p1YesButton.setLayoutY(535);
        p1YesButton.setPrefWidth(55);
        p1YesButton.setVisible(false);
        Button p2YesButton = new Button("Yes");
        p2YesButton.setTextAlignment(TextAlignment.CENTER);
        p2YesButton.setLayoutX(390);
        p2YesButton.setLayoutY(535);
        p2YesButton.setPrefWidth(55);
        p2YesButton.setVisible(false);
        Button p3YesButton = new Button("Yes");
        p3YesButton.setTextAlignment(TextAlignment.CENTER);
        p3YesButton.setLayoutX(690);
        p3YesButton.setLayoutY(535);
        p3YesButton.setPrefWidth(55);
        p3YesButton.setVisible(false);
        Button p4YesButton = new Button("Yes");
        p4YesButton.setTextAlignment(TextAlignment.CENTER);
        p4YesButton.setLayoutX(990);
        p4YesButton.setLayoutY(535);
        p4YesButton.setPrefWidth(55);
        p4YesButton.setVisible(false);
        group.getChildren().addAll(p1YesButton, p2YesButton, p3YesButton, p4YesButton);
        
        Button p1NoButton = new Button("No");
        p1NoButton.setTextAlignment(TextAlignment.CENTER);
        p1NoButton.setLayoutX(155);
        p1NoButton.setLayoutY(535);
        p1NoButton.setPrefWidth(55);
        p1NoButton.setVisible(false);
        Button p2NoButton = new Button("No");
        p2NoButton.setTextAlignment(TextAlignment.CENTER);
        p2NoButton.setLayoutX(455);
        p2NoButton.setLayoutY(535);
        p2NoButton.setPrefWidth(55);
        p2NoButton.setVisible(false);
        Button p3NoButton = new Button("No");
        p3NoButton.setTextAlignment(TextAlignment.CENTER);
        p3NoButton.setLayoutX(755);
        p3NoButton.setLayoutY(535);
        p3NoButton.setPrefWidth(55);
        p3NoButton.setVisible(false);
        Button p4NoButton = new Button("No");
        p4NoButton.setTextAlignment(TextAlignment.CENTER);
        p4NoButton.setLayoutX(1055);
        p4NoButton.setLayoutY(535);
        p4NoButton.setPrefWidth(55);
        p4NoButton.setVisible(false);
        group.getChildren().addAll(p1NoButton, p2NoButton, p3NoButton, p4NoButton);

        Button p1HitButton = new Button("Hit");
        p1HitButton.setTextAlignment(TextAlignment.CENTER);
        p1HitButton.setLayoutX(90);
        p1HitButton.setLayoutY(570);
        p1HitButton.setPrefWidth(55);
        p1HitButton.setVisible(false);
        Button p2HitButton = new Button("Hit");
        p2HitButton.setTextAlignment(TextAlignment.CENTER);
        p2HitButton.setLayoutX(390);
        p2HitButton.setLayoutY(570);
        p2HitButton.setPrefWidth(55);
        p2HitButton.setVisible(false);
        Button p3HitButton = new Button("Hit");
        p3HitButton.setTextAlignment(TextAlignment.CENTER);
        p3HitButton.setLayoutX(690);
        p3HitButton.setLayoutY(570);
        p3HitButton.setPrefWidth(55);
        p3HitButton.setVisible(false);
        Button p4HitButton = new Button("Hit");
        p4HitButton.setTextAlignment(TextAlignment.CENTER);
        p4HitButton.setLayoutX(990);
        p4HitButton.setLayoutY(570);
        p4HitButton.setPrefWidth(55);
        p4HitButton.setVisible(false);
        group.getChildren().addAll(p1HitButton, p2HitButton, p3HitButton, p4HitButton);
        
        Button p1StandButton = new Button("Stand");
        p1StandButton.setTextAlignment(TextAlignment.CENTER);
        p1StandButton.setLayoutX(155);
        p1StandButton.setLayoutY(570);
        p1StandButton.setPrefWidth(55);
        p1StandButton.setVisible(false);
        Button p2StandButton = new Button("Stand");
        p2StandButton.setTextAlignment(TextAlignment.CENTER);
        p2StandButton.setLayoutX(455);
        p2StandButton.setLayoutY(570);
        p2StandButton.setPrefWidth(55);
        p2StandButton.setVisible(false);
        Button p3StandButton = new Button("Stand");
        p3StandButton.setTextAlignment(TextAlignment.CENTER);
        p3StandButton.setLayoutX(755);
        p3StandButton.setLayoutY(570);
        p3StandButton.setPrefWidth(55);
        p3StandButton.setVisible(false);
        Button p4StandButton = new Button("Stand");
        p4StandButton.setTextAlignment(TextAlignment.CENTER);
        p4StandButton.setLayoutX(1055);
        p4StandButton.setLayoutY(570);
        p4StandButton.setPrefWidth(55);
        p4StandButton.setVisible(false);
        group.getChildren().addAll(p1StandButton, p2StandButton, p3StandButton, p4StandButton);

        Button p1DoubleButton = new Button("Double");
        p1DoubleButton.setTextAlignment(TextAlignment.CENTER);
        p1DoubleButton.setLayoutX(90);
        p1DoubleButton.setLayoutY(605);
        p1DoubleButton.setPrefWidth(55);
        p1DoubleButton.setVisible(false);
        Button p2DoubleButton = new Button("Double");
        p2DoubleButton.setTextAlignment(TextAlignment.CENTER);
        p2DoubleButton.setLayoutX(390);
        p2DoubleButton.setLayoutY(605);
        p2DoubleButton.setPrefWidth(55);
        p2DoubleButton.setVisible(false);
        Button p3DoubleButton = new Button("Double");
        p3DoubleButton.setTextAlignment(TextAlignment.CENTER);
        p3DoubleButton.setLayoutX(690);
        p3DoubleButton.setLayoutY(605);
        p3DoubleButton.setPrefWidth(55);
        p3DoubleButton.setVisible(false);
        Button p4DoubleButton = new Button("Double");
        p4DoubleButton.setTextAlignment(TextAlignment.CENTER);
        p4DoubleButton.setLayoutX(990);
        p4DoubleButton.setLayoutY(605);
        p4DoubleButton.setPrefWidth(55);
        p4DoubleButton.setVisible(false);
        group.getChildren().addAll(p1DoubleButton, p2DoubleButton, p3DoubleButton, p4DoubleButton);
        
        Button p1SplitButton = new Button("Split");
        p1SplitButton.setTextAlignment(TextAlignment.CENTER);
        p1SplitButton.setLayoutX(155);
        p1SplitButton.setLayoutY(605);
        p1SplitButton.setPrefWidth(55);
        p1SplitButton.setVisible(false);
        Button p2SplitButton = new Button("Split");
        p2SplitButton.setTextAlignment(TextAlignment.CENTER);
        p2SplitButton.setLayoutX(455);
        p2SplitButton.setLayoutY(605);
        p2SplitButton.setPrefWidth(55);
        p2SplitButton.setVisible(false);
        Button p3SplitButton = new Button("Split");
        p3SplitButton.setTextAlignment(TextAlignment.CENTER);
        p3SplitButton.setLayoutX(755);
        p3SplitButton.setLayoutY(605);
        p3SplitButton.setPrefWidth(55);
        p3SplitButton.setVisible(false);
        Button p4SplitButton = new Button("Split");
        p4SplitButton.setTextAlignment(TextAlignment.CENTER);
        p4SplitButton.setLayoutX(1055);
        p4SplitButton.setLayoutY(605);
        p4SplitButton.setPrefWidth(55);
        p4SplitButton.setVisible(false);
        group.getChildren().addAll(p1SplitButton, p2SplitButton, p3SplitButton, p4SplitButton);

        Button p1SurrenderButton = new Button("Surrender");
        p1SurrenderButton.setTextAlignment(TextAlignment.CENTER);
        p1SurrenderButton.setLayoutX(90);
        p1SurrenderButton.setLayoutY(640);
        p1SurrenderButton.setPrefWidth(120);
        p1SurrenderButton.setVisible(false);
        Button p2SurrenderButton = new Button("Surrender");
        p2SurrenderButton.setTextAlignment(TextAlignment.CENTER);
        p2SurrenderButton.setLayoutX(390);
        p2SurrenderButton.setLayoutY(640);
        p2SurrenderButton.setPrefWidth(120);
        p2SurrenderButton.setVisible(false);
        Button p3SurrenderButton = new Button("Surrender");
        p3SurrenderButton.setTextAlignment(TextAlignment.CENTER);
        p3SurrenderButton.setLayoutX(690);
        p3SurrenderButton.setLayoutY(640);
        p3SurrenderButton.setPrefWidth(120);
        p3SurrenderButton.setVisible(false);
        Button p4SurrenderButton = new Button("Surrender");
        p4SurrenderButton.setTextAlignment(TextAlignment.CENTER);
        p4SurrenderButton.setLayoutX(990);
        p4SurrenderButton.setLayoutY(640);
        p4SurrenderButton.setPrefWidth(120);
        p4SurrenderButton.setVisible(false);
        group.getChildren().addAll(p1SurrenderButton, p2SurrenderButton, p3SurrenderButton, p4SurrenderButton);

        // Start of Button Actions
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
            p1InUse = false;
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
            p2InUse = false;
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
            p3InUse = false;
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
            p4InUse = false;
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
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
                        throw new NumberFormatException(backEnd.getDm().playerTable.get(p2ID).getName());
                    }
                    if (p2WagerEntry <= 0) {
                        p2WagerField.clear();
                        p2WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
                        throw new IllegalArgumentException(backEnd.getDm().playerTable.get(p2ID).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(p2ID, -p2WagerEntry)) {
                        p2WagerField.clear();
                        p2WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
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
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry)) {
                            p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        }
                        throw new NumberFormatException(backEnd.getDm().playerTable.get(p3ID).getName());
                    }
                    if (p3WagerEntry <= 0) {
                        p3WagerField.clear();
                        p3WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry)) {
                            p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        }
                        throw new IllegalArgumentException(backEnd.getDm().playerTable.get(p3ID).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(p3ID, -p3WagerEntry)) {
                        p3WagerField.clear();
                        p3WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry)) {
                            p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        }
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
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry)) {
                            p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p3ID, p3WagerEntry)) {
                            p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p3ID).getCash());
                        }
                        throw new NumberFormatException(backEnd.getDm().playerTable.get(p4ID).getName());
                    }
                    if (p4WagerEntry <= 0) {
                        p4WagerField.clear();
                        p4WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry)) {
                            p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p3ID, p3WagerEntry)) {
                            p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p3ID).getCash());
                        }
                        throw new IllegalArgumentException(backEnd.getDm().playerTable.get(p4ID).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(p4ID, -p4WagerEntry)) {
                        p4WagerField.clear();
                        p4WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(p1ID, p1WagerEntry)) {
                            p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p2ID, p2WagerEntry)) {
                            p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(p3ID, p3WagerEntry)) {
                            p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p3ID).getCash());
                        }
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
                    p1WagerField.setVisible(false);
                    p1LeaveButton.setVisible(false);
                    p1CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p1ID).getCash());
                    p1WagerText.setText("Wager: $" + p1WagerEntry);
                    p1WagerText.setVisible(true);

                    p1HitButton.setVisible(true);
                    p1HitButton.setDisable(true);
                    p1StandButton.setVisible(true);
                    p1StandButton.setDisable(true);
                    p1DoubleButton.setVisible(true);
                    p1DoubleButton.setDisable(true);
                    p1SplitButton.setVisible(true);
                    p1SplitButton.setDisable(true);
                    p1SurrenderButton.setVisible(true);
                    p1SurrenderButton.setDisable(true);
                }
                if (p2InUse) {
                    p2WagerField.setVisible(false);
                    p2LeaveButton.setVisible(false);
                    p2CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p2ID).getCash());
                    p2WagerText.setText("Wager: $" + p2WagerEntry);
                    p2WagerText.setVisible(true);

                    p2HitButton.setVisible(true);
                    p2HitButton.setDisable(true);
                    p2StandButton.setVisible(true);
                    p2StandButton.setDisable(true);
                    p2DoubleButton.setVisible(true);
                    p2DoubleButton.setDisable(true);
                    p2SplitButton.setVisible(true);
                    p2SplitButton.setDisable(true);
                    p2SurrenderButton.setVisible(true);
                    p2SurrenderButton.setDisable(true);
                }
                if (p3InUse) {
                    p3WagerField.setVisible(false);
                    p3LeaveButton.setVisible(false);
                    p3CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p3ID).getCash());
                    p3WagerText.setText("Wager: $" + p3WagerEntry);
                    p3WagerText.setVisible(true);

                    p3HitButton.setVisible(true);
                    p3HitButton.setDisable(true);
                    p3StandButton.setVisible(true);
                    p3StandButton.setDisable(true);
                    p3DoubleButton.setVisible(true);
                    p3DoubleButton.setDisable(true);
                    p3SplitButton.setVisible(true);
                    p3SplitButton.setDisable(true);
                    p3SurrenderButton.setVisible(true);
                    p3SurrenderButton.setDisable(true);
                }
                if (p4InUse) {
                    p4WagerField.setVisible(false);
                    p4LeaveButton.setVisible(false);
                    p4CashText.setText("Cash: $" + backEnd.getDm().playerTable.get(p4ID).getCash());
                    p4WagerText.setText("Wager: $" + p4WagerEntry);
                    p4WagerText.setVisible(true);

                    p4HitButton.setVisible(true);
                    p4HitButton.setDisable(true);
                    p4StandButton.setVisible(true);
                    p4StandButton.setDisable(true);
                    p4DoubleButton.setVisible(true);
                    p4DoubleButton.setDisable(true);
                    p4SplitButton.setVisible(true);
                    p4SplitButton.setDisable(true);
                    p4SurrenderButton.setVisible(true);
                    p4SurrenderButton.setDisable(true);
                }

                // Deal Starting Cards
                int currentPlayer = -1;
                for (int i = 0; i < 2; i++) {
                    if (p4InUse) {
                        if (currentPlayer == -1) {
                            currentPlayer = 4;
                        }
    
                        backEnd.hitPlayer(p4ID);
                        numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                    }
                    if (p3InUse) {
                        if (currentPlayer == -1) {
                            currentPlayer = 3;
                        }
                        backEnd.hitPlayer(p3ID);
                        numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                    }
                    if (p2InUse) {
                        if (currentPlayer == -1) {
                            currentPlayer = 2;
                        }
                        backEnd.hitPlayer(p2ID);
                        numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                    }
                    if (p1InUse) {
                        if (currentPlayer == -1) {
                            currentPlayer = 1;
                        }
                        backEnd.hitPlayer(p1ID);
                        numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                    }
                    backEnd.hitDealer();
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));  
                }

                //TODO DEBUG CODE
                System.out.println("Dealer: " + backEnd.getDealer().getHand().toString());
                if (p1InUse) {
                    System.out.println("P1: " + backEnd.getDm().playerTable.get(p1ID).getHand().toString());
                }
                if (p2InUse) {
                    System.out.println("P2: " + backEnd.getDm().playerTable.get(p2ID).getHand().toString());
                }
                if (p3InUse) {
                    System.out.println("P3: " + backEnd.getDm().playerTable.get(p3ID).getHand().toString());
                }
                if (p4InUse) {
                    System.out.println("P4: " + backEnd.getDm().playerTable.get(p4ID).getHand().toString());
                }
                //TODO END OF DEBUG CODE

                if (backEnd.possibleDealerBlackjack()) { // TODO Figure out how insurance buttons will work
                    if (p1InUse) {
                        p1YesButton.setVisible(true);
                        p1NoButton.setVisible(true);
                    }
                    if (p2InUse) {
                        p2YesButton.setVisible(true);
                        p2NoButton.setVisible(true);
                    }
                    if (p3InUse) {
                        p3YesButton.setVisible(true);
                        p3NoButton.setVisible(true);
                    }
                    if (p4InUse) {
                        p4YesButton.setVisible(true);
                        p4NoButton.setVisible(true);
                    }
                } else {
                    if (currentPlayer == 4) {
                        if (backEnd.isPlayerBlackjack(p4ID)) { // BLACKJACK
                            currentPlayer--;
                        } else {
                            p4HitButton.setDisable(false);
                            p4StandButton.setDisable(false);
                            p4DoubleButton.setDisable(false);
                            p4SurrenderButton.setDisable(false);

                            if (backEnd.canSplit(p4ID)) {
                                p4SplitButton.setDisable(false);
                            }
                        }
                    } 
                    if (currentPlayer == 3) {
                        if (backEnd.isPlayerBlackjack(p3ID)) {
                            currentPlayer--;
                        } else {
                            p3HitButton.setDisable(false);
                            p3StandButton.setDisable(false);
                            p3DoubleButton.setDisable(false);
                            p3SurrenderButton.setDisable(false);

                            if (backEnd.canSplit(p3ID)) {
                                p4SplitButton.setDisable(false);
                            }
                        }
                    }
                    if (currentPlayer == 2) {
                        if (backEnd.isPlayerBlackjack(p2ID)) {
                            currentPlayer--;
                        } else {
                            p2HitButton.setDisable(false);
                            p2StandButton.setDisable(false);
                            p2DoubleButton.setDisable(false);
                            p2SurrenderButton.setDisable(false);

                            if (backEnd.canSplit(p2ID)) {
                                p4SplitButton.setDisable(false);
                            }
                        }
                    } 
                    if (currentPlayer == 1) {
                        if (backEnd.isPlayerBlackjack(p1ID)) {
                            currentPlayer--;
                        } else {
                            p1HitButton.setDisable(false);
                            p1StandButton.setDisable(false);
                            p1DoubleButton.setDisable(false);
                            p1SurrenderButton.setDisable(false);

                            if (backEnd.canSplit(p1ID)) {
                                p4SplitButton.setDisable(false);
                            }
                        }
                    }

                    if (currentPlayer == 0) { // EVERYONE HAS BLACKJACK
                        if (backEnd.isDealerBlackjack()) { // TODO FIGURE OUT HOW ROUND IS TERMINATED
                            // PUSH
                        } else {
                            // PLAYERS IN GAME WIN
                        }
                    }
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
