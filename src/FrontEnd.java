import java.util.ArrayList;
import java.util.List;
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

    private boolean[] pInUse;
    private int[] pIDs;
    private int[] pHandIndexes;
    private double[] pWagerEntries;
    
    private Hand[] activeHands;
    private ArrayList<ImageView> inPlayCards;

    private boolean reshuffleNeeded;
    private ImageView dealerHiddenCard;

    /**
     * Constructor for the front end
     */
    public FrontEnd() {
        greetingString = "Welcome to BlackjackFX!\nEnter Number of Decks:";

        pInUse = new boolean[4];
        pIDs = new int[4];
        for (int i = 0; i < pIDs.length; i++) {
            pIDs[i] = -1;
        }
        pHandIndexes = new int[4];
        pWagerEntries = new double[4];

        activeHands = new Hand[4];
        inPlayCards = new ArrayList<>();

        reshuffleNeeded = false;
        dealerHiddenCard = null;
    }

    /**
     * "Main" method for the front end application
     * 
     * @param engine A instance of the back end to use in the front end
     */
    public void run(BackEnd engine) {
        backEnd = engine;
        Application.launch();
    }

    /**
     * Method that prompts user to enter number of decks, then launches rest of the game
     * 
     * @param stage Stage to be used for the front end
     */
    @Override
    public void start(final Stage stage) {
        mStage = stage;
        TextInputDialog deckPrompt = new TextInputDialog(); // Create Window
        deckPrompt.setTitle("BlackjackFX"); // Set Window Title
        ((Stage) deckPrompt.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath)); // Set Window Image
        deckPrompt.setGraphic(new ImageView(new Image(Constants.blackjackLogoFilePath, 80, 115, true, true))); // Set Graphic in Window
        deckPrompt.setHeaderText(greetingString); // Set Text in Window
        deckPrompt.getEditor().setPromptText("Card Counting: 4 | Normal Game: 6"); // Set Text in Text Box
        
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

    /**
     * Method that sets up the layout grids
     * 
     * @param group The group for the front end
     */
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
        shoePile.setY(Constants.topCardsYPos);
        ImageView discardPile = new ImageView(Constants.backOfCardImage);
        discardPile.setX(65);
        discardPile.setY(25);
        discardPile.setVisible(false);
        ImageView cutCard = new ImageView(Constants.cutCardImage);
        cutCard.setX(Constants.shoePileXPos);
        cutCard.setY(Constants.topCardsYPos);
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
            Dialog<Pair<String, Double>> newAccountPrompt = new Dialog<>();
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
                        double cashEntry = Double.parseDouble(cashField.getText());
                        if (nameEntry.equals("") || cashEntry <= 0) {
                            throw new IllegalArgumentException();
                        }

                        return new Pair<>(nameEntry, cashEntry);
                    } catch (NumberFormatException e1) {
                        Alert e1Alert = new Alert(AlertType.ERROR);
                        e1Alert.setTitle("BlackjackFX");
                        ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                        e1Alert.setHeaderText("Error: Double must be Entered for Cash");
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

            Optional<Pair<String, Double>> result = newAccountPrompt.showAndWait();
            result.ifPresent(nameCashPair -> {
                backEnd.addNewPlayerToGame(nameCashPair.getKey(), nameCashPair.getValue());

                int playerIDNumber = backEnd.getDm().getLargestIDNumber();
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("BlackjackFX");
                ((Stage) successAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                successAlert.setHeaderText("Account Successfully Opened!\n" +
                    "Name: " + backEnd.getPlayer(playerIDNumber).getName() + "\n" +
                    "ID Number: " + playerIDNumber + "\n" +
                    "Balance: $" + backEnd.getPlayer(playerIDNumber).getCash());
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
            Dialog<Pair<Integer, Double>> manageCashPrompt = new Dialog<>();
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
                        double cashEntry = Double.parseDouble(cashField.getText().trim());
                        if (IDEntry <= 0 || cashEntry == 0) {
                            throw new IllegalArgumentException();
                        }

                        return new Pair<>(IDEntry, cashEntry);
                    } catch (NumberFormatException e1) {
                        Alert e1Alert = new Alert(AlertType.ERROR);
                        e1Alert.setTitle("BlackjackFX");
                        ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                        e1Alert.setHeaderText("Error: A Integer then a Double must be Entered in Fields");
                        e1Alert.show();
                    } catch (IllegalArgumentException e2) {
                        Alert e2Alert = new Alert(AlertType.ERROR);
                        e2Alert.setTitle("BlackjackFX");
                        ((Stage) e2Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                        e2Alert.setHeaderText("Error: ID must be Positive and Cash cannot be 0");
                        e2Alert.show();
                    }
                }
                return null;
            });

            Optional<Pair<Integer, Double>> result = manageCashPrompt.showAndWait();
            result.ifPresent(IDCashPair -> {
                int IDEntry = IDCashPair.getKey();
                double cashEntry = IDCashPair.getValue();

                if (backEnd.addOrSubtractCashToPlayer(IDEntry, cashEntry)) {
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("BlackjackFX");
                    ((Stage) successAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    successAlert.setHeaderText("Transaction Complete!\n" +
                        "Name: " + backEnd.getPlayer(IDEntry).getName() + "\n" +
                        "ID Number: " + backEnd.getPlayer(IDEntry).getIDNumber() + "\n" +
                        "Balance: $" + backEnd.getPlayer(IDEntry).getCash());
                    successAlert.show();
                } else {
                    Alert failAlert = new Alert(AlertType.ERROR);
                    failAlert.setTitle("BlackjackFX");
                    ((Stage) failAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    if (backEnd.checkIfPlayerExists(IDEntry)) {
                        failAlert.setHeaderText("Error: Insufficient Funds\n" +
                        "Name: " + backEnd.getPlayer(IDEntry).getName() + "\n" +
                        "ID Number: " + backEnd.getPlayer(IDEntry).getIDNumber() + "\n" +
                        "Balance: $" + backEnd.getPlayer(IDEntry).getCash());
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

        Text p1CashText = new Text(); // TODO: Figure out decimal formatting
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

                pIDs[0] = IDEntry;
                p1IDField.setVisible(false);
                p1SubmitButton.setVisible(false);

                p1WagerField.clear();
                p1WagerField.setVisible(true);
                p1LeaveButton.setVisible(true);
                p1IDAndNameText.setText("ID: " + IDEntry + "\nName: " + backEnd.getPlayer(IDEntry).getName());
                p1CashText.setText("Cash: $" + backEnd.getPlayer(IDEntry).getCash());
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

                pIDs[1] = IDEntry;
                p2IDField.setVisible(false);
                p2SubmitButton.setVisible(false);

                p2WagerField.clear();
                p2WagerField.setVisible(true);
                p2LeaveButton.setVisible(true);
                p2IDAndNameText.setText("ID: " + IDEntry + "\nName: " + backEnd.getPlayer(IDEntry).getName());
                p2CashText.setText("Cash: $" + backEnd.getPlayer(IDEntry).getCash());
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

                pIDs[2] = IDEntry;
                p3IDField.setVisible(false);
                p3SubmitButton.setVisible(false);

                p3WagerField.clear();
                p3WagerField.setVisible(true);
                p3LeaveButton.setVisible(true);
                p3IDAndNameText.setText("ID: " + IDEntry + "\nName: " + backEnd.getPlayer(IDEntry).getName());
                p3CashText.setText("Cash: $" + backEnd.getPlayer(IDEntry).getCash());
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

                pIDs[3] = IDEntry;
                p4IDField.setVisible(false);
                p4SubmitButton.setVisible(false);

                p4WagerField.clear();
                p4WagerField.setVisible(true);
                p4LeaveButton.setVisible(true);
                p4IDAndNameText.setText("ID: " + IDEntry + "\nName: " + backEnd.getPlayer(IDEntry).getName());
                p4CashText.setText("Cash: $" + backEnd.getPlayer(IDEntry).getCash());
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
            pInUse[0] = false;
            pIDs[0] = -1;
            p1WagerField.setVisible(false);
            p1LeaveButton.setVisible(false);
            p1IDAndNameText.setVisible(false);
            p1CashText.setVisible(false);
            if (pIDs[0] == -1 && pIDs[1] == -1 && pIDs[2] == -1 && pIDs[3] == -1) {
                dealButton.setVisible(false);
            }

            p1IDField.clear();
            p1IDField.setVisible(true);
            p1SubmitButton.setVisible(true);
        });
        p2LeaveButton.setOnAction((event) -> {
            pInUse[1] = false;
            pIDs[1] = -1;
            p2WagerField.setVisible(false);
            p2LeaveButton.setVisible(false);
            p2IDAndNameText.setVisible(false);
            p2CashText.setVisible(false);
            if (pIDs[0] == -1 && pIDs[1] == -1 && pIDs[2] == -1 && pIDs[3] == -1) {
                dealButton.setVisible(false);
            }

            p2IDField.clear();
            p2IDField.setVisible(true);
            p2SubmitButton.setVisible(true);
        });
        p3LeaveButton.setOnAction((event) -> {
            pInUse[2] = false;
            pIDs[2] = -1;
            p3WagerField.setVisible(false);
            p3LeaveButton.setVisible(false);
            p3IDAndNameText.setVisible(false);
            p3CashText.setVisible(false);
            if (pIDs[0] == -1 && pIDs[1] == -1 && pIDs[2] == -1 && pIDs[3] == -1) {
                dealButton.setVisible(false);
            }

            p3IDField.clear();
            p3IDField.setVisible(true);
            p3SubmitButton.setVisible(true);
        });
        p4LeaveButton.setOnAction((event) -> {
            pInUse[3] = false;
            pIDs[3] = -1;
            p4WagerField.setVisible(false);
            p4LeaveButton.setVisible(false);
            p4IDAndNameText.setVisible(false);
            p4CashText.setVisible(false);
            if (pIDs[0] == -1 && pIDs[1] == -1 && pIDs[2] == -1 && pIDs[3] == -1) {
                dealButton.setVisible(false);
            }

            p4IDField.clear();
            p4IDField.setVisible(true);
            p4SubmitButton.setVisible(true);
        });

        dealButton.setOnAction((event) -> {
            try {
                // Checks for Valid Input
                if (p1WagerField.isVisible()) {
                    try {
                        pWagerEntries[0] = Double.parseDouble(p1WagerField.getText().trim());
                    } catch (NumberFormatException e) {
                        p1WagerField.clear();
                        p1WagerField.requestFocus();
                        throw new NumberFormatException(backEnd.getPlayer(pIDs[0]).getName());
                    }
                    if (pWagerEntries[0] <= 0) {
                        p1WagerField.clear();
                        p1WagerField.requestFocus();
                        throw new IllegalArgumentException(backEnd.getPlayer(pIDs[0]).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(pIDs[0], -pWagerEntries[0])) {
                        p1WagerField.clear();
                        p1WagerField.requestFocus();
                        throw new ArithmeticException(backEnd.getPlayer(pIDs[0]).getName());
                    }

                    pInUse[0] = true;
                }
                if (p2WagerField.isVisible()) {
                    try {
                        pWagerEntries[1] = Double.parseDouble(p2WagerField.getText().trim());
                    } catch (NumberFormatException e) {
                        p2WagerField.clear();
                        p2WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        throw new NumberFormatException(backEnd.getPlayer(pIDs[1]).getName());
                    }
                    if (pWagerEntries[1] <= 0) {
                        p2WagerField.clear();
                        p2WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        throw new IllegalArgumentException(backEnd.getPlayer(pIDs[1]).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(pIDs[1], -pWagerEntries[1])) {
                        p2WagerField.clear();
                        p2WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        throw new ArithmeticException(backEnd.getPlayer(pIDs[1]).getName());
                    }

                    pInUse[1] = true;
                }
                if (p3WagerField.isVisible()) {
                    try {
                        pWagerEntries[2] = Double.parseDouble(p3WagerField.getText().trim());
                    } catch (NumberFormatException e) {
                        p3WagerField.clear();
                        p3WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[1], pWagerEntries[1])) {
                            p2CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[1]).getCash());
                        }
                        throw new NumberFormatException(backEnd.getPlayer(pIDs[2]).getName());
                    }
                    if (pWagerEntries[2] <= 0) {
                        p3WagerField.clear();
                        p3WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[1], pWagerEntries[1])) {
                            p2CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[1]).getCash());
                        }
                        throw new IllegalArgumentException(backEnd.getPlayer(pIDs[2]).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(pIDs[2], -pWagerEntries[2])) {
                        p3WagerField.clear();
                        p3WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[1], pWagerEntries[1])) {
                            p2CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[1]).getCash());
                        }
                        throw new ArithmeticException(backEnd.getPlayer(pIDs[2]).getName());
                    }

                    pInUse[2] = true;
                }
                if (p4WagerField.isVisible()) {
                    try {
                        pWagerEntries[3] = Double.parseDouble(p4WagerField.getText().trim());
                    } catch (NumberFormatException e) {
                        p4WagerField.clear();
                        p4WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[1], pWagerEntries[1])) {
                            p2CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[1]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[2], pWagerEntries[2])) {
                            p3CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[2]).getCash());
                        }
                        throw new NumberFormatException(backEnd.getPlayer(pIDs[3]).getName());
                    }
                    if (pWagerEntries[3] <= 0) {
                        p4WagerField.clear();
                        p4WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[1], pWagerEntries[1])) {
                            p2CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[1]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[2], pWagerEntries[2])) {
                            p3CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[2]).getCash());
                        }
                        throw new IllegalArgumentException(backEnd.getPlayer(pIDs[3]).getName());
                    }
                    if (!backEnd.addOrSubtractCashToPlayer(pIDs[3], -pWagerEntries[3])) {
                        p4WagerField.clear();
                        p4WagerField.requestFocus();
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[0], pWagerEntries[0])) {
                            p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[1], pWagerEntries[1])) {
                            p2CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[1]).getCash());
                        }
                        if (backEnd.addOrSubtractCashToPlayer(pIDs[2], pWagerEntries[2])) {
                            p3CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[2]).getCash());
                        }
                        throw new ArithmeticException(backEnd.getPlayer(pIDs[3]).getName());
                    }

                    pInUse[3] = true;
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

                if (pInUse[0]) {
                    p1WagerField.setVisible(false);
                    p1LeaveButton.setVisible(false);
                    p1CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[0]).getCash());
                    p1WagerText.setText("Wager: $" + pWagerEntries[0]);
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
                if (pInUse[1]) {
                    p2WagerField.setVisible(false);
                    p2LeaveButton.setVisible(false);
                    p2CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[1]).getCash());
                    p2WagerText.setText("Wager: $" + pWagerEntries[1]);
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
                if (pInUse[2]) {
                    p3WagerField.setVisible(false);
                    p3LeaveButton.setVisible(false);
                    p3CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[2]).getCash());
                    p3WagerText.setText("Wager: $" + pWagerEntries[2]);
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
                if (pInUse[3]) {
                    p4WagerField.setVisible(false);
                    p4LeaveButton.setVisible(false);
                    p4CashText.setText("Cash: $" + backEnd.getPlayer(pIDs[3]).getCash());
                    p4WagerText.setText("Wager: $" + pWagerEntries[3]);
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
                pHandIndexes[0] = 0;
                pHandIndexes[1] = 0;
                pHandIndexes[2] = 0;
                pHandIndexes[3] = 0;
                int currentPlayer = -1;

                if (pInUse[3]) {
                    if (currentPlayer == -1) {
                        currentPlayer = 4;
                    }
    
                    activeHands[3] = backEnd.getPlayer(pIDs[3]).getHands().get(pHandIndexes[3]); // TODO ADDED
                    if (backEnd.hitPlayerHand(activeHands[3])) {
                        reshuffleNeeded = true;
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(pIDs[3], pHandIndexes[3], group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                }
                if (pInUse[2]) {
                    if (currentPlayer == -1) {
                        currentPlayer = 3;
                    }

                    if (pIDs[2] == pIDs[3]) {
                        pHandIndexes[2] = backEnd.getPlayer(pIDs[2]).addNewHand();
                    }
                    
                    activeHands[2] = backEnd.getPlayer(pIDs[2]).getHands().get(pHandIndexes[2]); // TODO ADDED
                    if (backEnd.hitPlayerHand(activeHands[2])) {
                        reshuffleNeeded = true;
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(pIDs[2], pHandIndexes[2], group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                }
                if (pInUse[1]) {
                    if (currentPlayer == -1) {
                        currentPlayer = 2;
                    }

                    if (pIDs[1] == pIDs[2] || pIDs[1] == pIDs[3]) {
                        pHandIndexes[1] = backEnd.getPlayer(pIDs[1]).addNewHand();
                    }

                    activeHands[1] = backEnd.getPlayer(pIDs[1]).getHands().get(pHandIndexes[1]); // TODO ADDED
                    if (backEnd.hitPlayerHand(activeHands[1])) {
                        reshuffleNeeded = true;
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(pIDs[1], pHandIndexes[1], group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                }
                if (pInUse[0]) {
                    if (currentPlayer == -1) {
                        currentPlayer = 1;
                    }

                    if (pIDs[0] == pIDs[1] || pIDs[0] == pIDs[2] || pIDs[0] == pIDs[3]) {
                        pHandIndexes[0] = backEnd.getPlayer(pIDs[0]).addNewHand();
                    }

                    activeHands[0] = backEnd.getPlayer(pIDs[0]).getHands().get(pHandIndexes[0]); // TODO ADDED
                    if (backEnd.hitPlayerHand(activeHands[0])) {
                        reshuffleNeeded = true;
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(pIDs[0], pHandIndexes[0], group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                }
                if (backEnd.hitDealer()) {
                    reshuffleNeeded = true;
                    displayCutCard(group);
                }
                inPlayCards.add(displayCard(-2, -2, group));
                numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                
                if (pInUse[3]) {
                    if (backEnd.hitPlayerHand(activeHands[3])) {
                        reshuffleNeeded = true;
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(pIDs[3], pHandIndexes[3], group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                }
                if (pInUse[2]) {
                    if (backEnd.hitPlayerHand(activeHands[2])) {
                        reshuffleNeeded = true;
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(pIDs[2], pHandIndexes[2], group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                }
                if (pInUse[1]) {
                    if (backEnd.hitPlayerHand(activeHands[1])) {
                        reshuffleNeeded = true;
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(pIDs[1], pHandIndexes[1], group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                }
                if (pInUse[0]) {
                    if (backEnd.hitPlayerHand(activeHands[0])) {
                        reshuffleNeeded = true;
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(pIDs[0], pHandIndexes[0], group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                }
                if (backEnd.hitDealer()) {
                    reshuffleNeeded = true;
                    displayCutCard(group);
                }
                dealerHiddenCard = displayCard(-2, -2, group);
                inPlayCards.add(dealerHiddenCard);
                numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));

                // Checks to make sure insurance isn't needed and dealer doesn't have Blackjack
                if (backEnd.insuranceNeeded()) { // Insurance (Dealer showing an Ace)
                    // FIXME: Following code is very ugly
                    // Checks what buttons should be visible and makes sure they are disabled
                    if (pInUse[3] && !backEnd.isPlayerHandBlackjack(activeHands[3])) {
                        p4YesButton.setVisible(true);
                        p4NoButton.setVisible(true);
                        p4YesButton.setDisable(true);
                        p4NoButton.setDisable(true);
                    }
                    if (pInUse[2] && !backEnd.isPlayerHandBlackjack(activeHands[2])) {
                        p3YesButton.setVisible(true);
                        p3NoButton.setVisible(true);
                        p3YesButton.setDisable(true);
                        p3NoButton.setDisable(true);
                    }
                    if (pInUse[1] && !backEnd.isPlayerHandBlackjack(activeHands[1])) {
                        p2YesButton.setVisible(true);
                        p2NoButton.setVisible(true);
                        p2YesButton.setDisable(true);
                        p2NoButton.setDisable(true);
                    }
                    if (pInUse[0] && !backEnd.isPlayerHandBlackjack(activeHands[0])) {
                        p1YesButton.setVisible(true);
                        p1NoButton.setVisible(true);
                        p1YesButton.setDisable(true);
                        p1NoButton.setDisable(true);
                    }

                    // Finds the first player without Blackjack and enables their buttons
                    if (pInUse[3] && !backEnd.isPlayerHandBlackjack(activeHands[3])) {
                        p4YesButton.setDisable(false);
                        p4NoButton.setDisable(false);
                    } else if (pInUse[2] && !backEnd.isPlayerHandBlackjack(activeHands[2])) {
                        p3YesButton.setDisable(false);
                        p3NoButton.setDisable(false);
                    } else if (pInUse[1] && !backEnd.isPlayerHandBlackjack(activeHands[1])) {
                        p2YesButton.setDisable(false);
                        p2NoButton.setDisable(false);
                    } else if (pInUse[0] && !backEnd.isPlayerHandBlackjack(activeHands[0])) {
                        p1YesButton.setDisable(false);
                        p1NoButton.setDisable(false);
                    } else { // EVERYONE HAS BLACKJACK
                        showDealerHiddenCard(group);
                        if (backEnd.isDealerBlackjack()) { // TODO FIGURE OUT HOW ROUND IS TERMINATED
                            if (pInUse[3]) { // Push TODO UPDATE TEXT
                                backEnd.payPlayer(pIDs[3], pWagerEntries[3], 2);
                            }
                            if (pInUse[2]) {
                                backEnd.payPlayer(pIDs[2], pWagerEntries[2], 2);
                            }
                            if (pInUse[1]) {
                                backEnd.payPlayer(pIDs[1], pWagerEntries[1], 2);
                            }
                            if (pInUse[0]) {
                                backEnd.payPlayer(pIDs[0], pWagerEntries[0], 2);
                            }
                            // Terminate round
                        } else {
                            if (pInUse[3]) { // Players win TODO UPDATE TEXT, SHOW BLACKJACK CELEBRATION?
                                backEnd.payPlayer(pIDs[3], pWagerEntries[3], 3);
                            }
                            if (pInUse[2]) {
                                backEnd.payPlayer(pIDs[2], pWagerEntries[2], 3);
                            }
                            if (pInUse[1]) {
                                backEnd.payPlayer(pIDs[1], pWagerEntries[1], 3);
                            }
                            if (pInUse[0]) {
                                backEnd.payPlayer(pIDs[0], pWagerEntries[0], 3);
                            }
                        }
                    }
                } else { // No Insurance
                    if (backEnd.isDealerBlackjack()) { // Dealer has Blackjack (Showing Value 10)
                        showDealerHiddenCard(group);
                        if (pInUse[3] && backEnd.isPlayerHandBlackjack(activeHands[3])) {
                            backEnd.payPlayer(pIDs[3], pWagerEntries[3], 2);
                        }
                        if (pInUse[2] && backEnd.isPlayerHandBlackjack(activeHands[3])) {
                            backEnd.payPlayer(pIDs[2], pWagerEntries[2], 2);
                        }
                        if (pInUse[1] && backEnd.isPlayerHandBlackjack(activeHands[1])) {
                            backEnd.payPlayer(pIDs[1], pWagerEntries[1], 2);
                        }
                        if (pInUse[0] && backEnd.isPlayerHandBlackjack(activeHands[0])) {
                            backEnd.payPlayer(pIDs[0], pWagerEntries[0], 2);
                        }
                    } else { // Dealer does not have Blackjack
                        // Determine first player without Blackjack
                        if (pInUse[3] && !backEnd.isPlayerHandBlackjack(activeHands[3])) {
                            p4HitButton.setDisable(false);
                            p4StandButton.setDisable(false);
                            p4SurrenderButton.setDisable(false);
                            if (backEnd.canDouble(pIDs[3], pWagerEntries[3])) {
                                p4DoubleButton.setDisable(false);
                            }
                            if (backEnd.canSplit(pIDs[3], pWagerEntries[3], pHandIndexes[3])) {
                                p4SplitButton.setDisable(false);
                            }
                        } else if (pInUse[2] && !backEnd.isPlayerHandBlackjack(activeHands[2])) {
                            p3HitButton.setDisable(false);
                            p3StandButton.setDisable(false);
                            p3SurrenderButton.setDisable(false);
                            if (backEnd.canDouble(pIDs[2], pWagerEntries[2])) {
                                p3DoubleButton.setDisable(false);
                            }
                            if (backEnd.canSplit(pIDs[2], pWagerEntries[2], pHandIndexes[2])) {
                                p3SplitButton.setDisable(false);
                            }
                        } else if (pInUse[1] && !backEnd.isPlayerHandBlackjack(activeHands[1])) {
                            p2HitButton.setDisable(false);
                            p2StandButton.setDisable(false);
                            p2SurrenderButton.setDisable(false);
                            if (backEnd.canDouble(pIDs[1], pWagerEntries[1])) {
                                p2DoubleButton.setDisable(false);
                            }
                            if (backEnd.canSplit(pIDs[1], pWagerEntries[1], pHandIndexes[1])) {
                                p2SplitButton.setDisable(false);
                            }
                        } else if (pInUse[0] && !backEnd.isPlayerHandBlackjack(activeHands[0])) {
                            p1HitButton.setDisable(false);
                            p1StandButton.setDisable(false);
                            p1SurrenderButton.setDisable(false);
                            if (backEnd.canDouble(pIDs[0], pWagerEntries[0])) {
                                p1DoubleButton.setDisable(false);
                            }
                            if (backEnd.canSplit(pIDs[0], pWagerEntries[0], pHandIndexes[0])) {
                                p1SplitButton.setDisable(false);
                            }
                        } else { // Everyone has Blackjack and Dealer does not
                            showDealerHiddenCard(group);
                            if (pInUse[3]) { // Players win TODO UPDATE TEXT? SHOW BLACKJACK CELEBRATION?
                                backEnd.payPlayer(pIDs[3], pWagerEntries[3], 3);
                            }
                            if (pInUse[2]) {
                                backEnd.payPlayer(pIDs[2], pWagerEntries[2], 3);
                            }
                            if (pInUse[1]) {
                                backEnd.payPlayer(pIDs[1], pWagerEntries[1], 3);
                            }
                            if (pInUse[0]) {
                                backEnd.payPlayer(pIDs[0], pWagerEntries[0], 3);
                            }
                        }
                    }
                }

                // TODO Remove these, temp prints
                // List<Card> p1CardList = backEnd.getPlayer(pIDs[0]).getHands().get(pHandIndexes[0]).getCardList();
                // List<Card> p2CardList = backEnd.getPlayer(pIDs[1]).getHands().get(pHandIndexes[1]).getCardList();
                // List<Card> p3CardList = backEnd.getPlayer(pIDs[2]).getHands().get(pHandIndexes[2]).getCardList();
                // List<Card> p4CardList = backEnd.getPlayer(pIDs[3]).getHands().get(pHandIndexes[3]).getCardList();
                // System.out.println("P1 HAND: " + p1CardList);
                // System.out.println("P2 HAND: " + p2CardList);
                // System.out.println("P3 HAND: " + p3CardList);
                // System.out.println("P4 HAND: " + p4CardList);
                // System.out.println(activeHands[0].getCardList());
                System.out.println("DEALER HAND: " + backEnd.getDealer().getHand());
                System.out.println("CARD LIST SIZE: " + backEnd.getDecks().getCardList().size());
                System.out.println("USED LIST: " + backEnd.getDecks().getUsedCardList());

            } catch (NumberFormatException e1) {
                Alert e1Alert = new Alert(AlertType.ERROR);
                e1Alert.setTitle("BlackjackFX");
                ((Stage) e1Alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                e1Alert.setHeaderText("Error: " + e1.getMessage() + "'s Wager must be a Double");
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

    /**
     * 
     * 
     * @param playerID ID of the player to display the card to, use -2 for the dealer
     * @param handIndex Hand Index to display the card to, used -2 for the dealer
     * @param group The group for the front end
     * @return The ImageView of the card in children list
     */
    private ImageView displayCard(int playerID, int handIndex, Group group) {
        List<Card> handCardList;
        int cardXPos;
        int cardYPos;

        if (playerID == -2 && handIndex == -2) {
            handCardList = backEnd.getDealer().getHand().getCardList();
            cardXPos = Constants.dealerCardStartingXPos + ((handCardList.size() - 1) * Constants.layeredCardOffset);
            cardYPos = Constants.dealerCardFrontYPos;
        } else {
            handCardList = backEnd.getPlayer(playerID).getHands().get(handIndex).getCardList();

            if (playerID == pIDs[3] && handIndex == pHandIndexes[3]) {
                cardXPos = Constants.p4CardStartingXPos + ((handCardList.size() - 1) * Constants.layeredCardOffset);
            } else if (playerID == pIDs[2] && handIndex == pHandIndexes[2]) {
                cardXPos = Constants.p3CardStartingXPos + ((handCardList.size() - 1) * Constants.layeredCardOffset);
            } else if (playerID == pIDs[1] && handIndex == pHandIndexes[1]) {
                cardXPos = Constants.p2CardStartingXPos + ((handCardList.size() - 1) * Constants.layeredCardOffset);
            } else {
                cardXPos = Constants.p1CardStartingXPos + ((handCardList.size() - 1) * Constants.layeredCardOffset);  
            }
            cardYPos = Constants.playerCardFrontYPos;
        }
        
        Image cardImage;
        if (cardXPos == Constants.dealerCardStartingXPos + Constants.layeredCardOffset) {
            cardImage = Constants.backOfCardImage;
            cardYPos = Constants.dealerCardBackYPos;
        } else {
            Card cardToAdd = handCardList.get(handCardList.size() - 1);
            cardImage = determineCardImage(cardToAdd);
        }

        ImageView card = new ImageView(cardImage);
        card.setX(cardXPos);
        card.setY(cardYPos);
        card.setVisible(true);
        group.getChildren().add(card);
        
        return card;
    }

    /**
     * 
     * 
     * @param group The group for the front end
     */
    private void showDealerHiddenCard(Group group) {
        group.getChildren().remove(dealerHiddenCard);
        inPlayCards.remove(dealerHiddenCard);

        Card cardToAdd = backEnd.getDealer().getHand().getCardList().get(1);
        Image cardImage = determineCardImage(cardToAdd);

        ImageView card = new ImageView(cardImage);
        card.setX(Constants.dealerCardStartingXPos + Constants.layeredCardOffset);
        card.setY(Constants.dealerCardFrontYPos);
        card.setVisible(true);
        group.getChildren().add(card);
        inPlayCards.add((ImageView) group.getChildren().get(group.getChildren().size() - 1));
    }

    /**
     * 
     * 
     * @param cardToAdd
     * @return
     */
    private Image determineCardImage(Card cardToAdd) {
        Image cardImage = null;

        switch (cardToAdd.getValue()) {
            case Ace:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubAceImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeAceImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondAceImage;
                } else {
                    cardImage = Constants.heartAceImage;
                }
                break;
            case Two:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubTwoImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeTwoImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondTwoImage;
                } else {
                    cardImage = Constants.heartTwoImage;
                }
                break;
            case Three:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubThreeImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeThreeImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondThreeImage;
                } else {
                    cardImage = Constants.heartThreeImage;
                }
                break;
            case Four:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubFourImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeFourImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondFourImage;
                } else {
                    cardImage = Constants.heartFourImage;
                }
                break;
            case Five:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubFiveImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeFiveImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondFiveImage;
                } else {
                    cardImage = Constants.heartFiveImage;
                }
                break;
            case Six:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubSixImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeSixImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondSixImage;
                } else {
                    cardImage = Constants.heartSixImage;
                }
                break;
            case Seven:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubSevenImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeSevenImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondSevenImage;
                } else {
                    cardImage = Constants.heartSevenImage;
                }
                break;
            case Eight:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubEightImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeEightImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondEightImage;
                } else {
                    cardImage = Constants.heartEightImage;
                }
                break;
            case Nine:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubNineImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeNineImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondNineImage;
                } else {
                    cardImage = Constants.heartNineImage;
                }
                break;
            case Ten:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubTenImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeTenImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondTenImage;
                } else {
                    cardImage = Constants.heartTenImage;
                }
                break;
            case Jack:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubJackImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeJackImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondJackImage;
                } else {
                    cardImage = Constants.heartJackImage;
                }
                break;
            case Queen:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubQueenImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeQueenImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondQueenImage;
                } else {
                    cardImage = Constants.heartQueenImage;
                }
                break;
            case King:
                if (cardToAdd.getSuit().equals(Card.Suit.Club)) {
                    cardImage = Constants.clubKingImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Spade)) {
                    cardImage = Constants.spadeKingImage;
                } else if (cardToAdd.getSuit().equals(Card.Suit.Diamond)) {
                    cardImage = Constants.diamondKingImage;
                } else {
                    cardImage = Constants.heartKingImage;
                }
                break;
            default:
                break;
        }

        return cardImage;
    }
    
    /**
     * 
     * 
     * @param group The group for the front end
     */
    private void displayCutCard(Group group) {
        ImageView cutCard = new ImageView(Constants.cutCardImage);
        cutCard.setX(Constants.cutCardXPos);
        cutCard.setY(Constants.topCardsYPos);
        cutCard.setVisible(true);
        group.getChildren().add(cutCard);
    }
}
