import java.util.ArrayList;
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
    
    /*
     * Each Possible Hand (After 4 Splits) at Table is Represented by a Pair<Player, Integer>.
     * From Left to Right:
     * Seat 1) Index 0
     * Seat 2) Index 1
     * Seat 3) Index 2
     * Seat 4) Index 3
     */
    private Player[] players;
    private ArrayList<ImageView> inPlayCards;
    private String greetingString;
    private ImageView dealerHiddenCard;

    /**
     * Constructor for the front end
     */
    public FrontEnd() {
        players = new Player[4];
        inPlayCards = new ArrayList<>();
        greetingString = "Welcome to BlackjackFX!\nEnter Number of Decks:";
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

                backEnd.createDecks(numOfDecks);
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

        setLayoutGrid(group);

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

                int playerIDNumber = backEnd.getLargestDmIDNumber();
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
                int[] resultIDs = backEnd.lookupPlayerID(lookUpName);
                if (resultIDs.length == 0) {
                    Alert personDNEAlert = new Alert(AlertType.ERROR);
                    personDNEAlert.setTitle("BlackjackFX");
                    ((Stage) personDNEAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    personDNEAlert.setHeaderText("Player Does not Exist, Open a New Account");
                    personDNEAlert.show();
                } else {
                    Alert personIDAlert = new Alert(AlertType.INFORMATION);
                    personIDAlert.setTitle("BlackjackFX");
                    ((Stage) personIDAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    personIDAlert.setHeaderText("The Following ID Numbers were Found:");
                    String lookupIDOutput = "";
                    for (int i = 0; i < resultIDs.length; i++) {
                        lookupIDOutput += resultIDs[i] + ": " + backEnd.getPlayer(resultIDs[i]).getName() + "\n";
                    }
                    personIDAlert.setContentText(lookupIDOutput);
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

                if (backEnd.manageCash(IDEntry, cashEntry)) {
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("BlackjackFX");
                    ((Stage) successAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    successAlert.setHeaderText("Transaction Complete!\n" +
                        "Name: " + backEnd.getPlayer(IDEntry).getName() + "\n" +
                        "ID Number: " + backEnd.getPlayer(IDEntry).getID() + "\n" +
                        "Balance: $" + backEnd.getPlayer(IDEntry).getCash());
                    successAlert.show();
                } else {
                    Alert failAlert = new Alert(AlertType.ERROR);
                    failAlert.setTitle("BlackjackFX");
                    ((Stage) failAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Constants.blackjackLogoFilePath));
                    if (backEnd.checkIfPlayerExists(IDEntry)) {
                        failAlert.setHeaderText("Error: Insufficient Funds\n" +
                        "Name: " + backEnd.getPlayer(IDEntry).getName() + "\n" +
                        "ID Number: " + backEnd.getPlayer(IDEntry).getID() + "\n" +
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
        TextField[] pIDFields = new TextField[4];
        for (int i = 0; i < pIDFields.length; i++) {
            pIDFields[i] = new TextField();
            pIDFields[i].setPromptText("Enter ID #");
            pIDFields[i].setLayoutX((i * 300) + 115);
            pIDFields[i].setLayoutY(570);
            pIDFields[i].setPrefWidth(70);
        }
        group.getChildren().addAll(pIDFields);

        Button[] pSubmitButtons = new Button[4];
        for (int i = 0; i < pSubmitButtons.length; i++) {
            pSubmitButtons[i] = new Button("Submit");
            pSubmitButtons[i].setTextAlignment(TextAlignment.CENTER);
            pSubmitButtons[i].setLayoutX((i * 300) + 115);
            pSubmitButtons[i].setLayoutY(605);
            pSubmitButtons[i].setPrefWidth(70);
        }
        group.getChildren().addAll(pSubmitButtons);

        TextField[] pWagerFields = new TextField[4];
        for (int i = 0; i < pWagerFields.length; i++) {
            pWagerFields[i] = new TextField();
            pWagerFields[i].setPromptText("Wager $");
            pWagerFields[i].setPrefWidth(70);
            pWagerFields[i].setLayoutX((i * 300) + 115);
            pWagerFields[i].setLayoutY(570);
            pWagerFields[i].setVisible(false);
        }
        group.getChildren().addAll(pWagerFields);

        Button[] pLeaveButtons = new Button[4];
        for (int i = 0; i < pLeaveButtons.length; i++) {
            pLeaveButtons[i] = new Button("Leave");
            pLeaveButtons[i].setTextAlignment(TextAlignment.CENTER);
            pLeaveButtons[i].setLayoutX((i * 300) + 115);
            pLeaveButtons[i].setLayoutY(605);
            pLeaveButtons[i].setPrefWidth(70);
            pLeaveButtons[i].setVisible(false);
        }
        group.getChildren().addAll(pLeaveButtons);

        Text[] pIDAndNameTexts = new Text[4];
        for (int i = 0; i < pIDAndNameTexts.length; i++) {
            pIDAndNameTexts[i] = new Text();
            pIDAndNameTexts[i].setFont(Font.font("Verdana", 12));
            pIDAndNameTexts[i].setFill(Color.GHOSTWHITE);
            pIDAndNameTexts[i].setX((i * 300) + 5);
            pIDAndNameTexts[i].setY(666);
            pIDAndNameTexts[i].setVisible(false);
        }
        group.getChildren().addAll(pIDAndNameTexts);

        Text[] pCashTexts = new Text[4];
        for (int i = 0; i < pCashTexts.length; i++) {
            pCashTexts[i] = new Text();
            pCashTexts[i].setFont(Font.font("Verdana", 12));
            pCashTexts[i].setFill(Color.GHOSTWHITE);
            pCashTexts[i].setX((i * 300) + 5);
            pCashTexts[i].setY(695);
            pCashTexts[i].setVisible(false);
        }
        group.getChildren().addAll(pCashTexts);

        Button dealButton = new Button("DEAL");
        dealButton.setFont(new Font(20));
        dealButton.setPrefWidth(74);
        dealButton.setPrefHeight(40);
        dealButton.setLayoutX(563);
        dealButton.setLayoutY(580);
        dealButton.setTextAlignment(TextAlignment.CENTER);
        dealButton.setVisible(false);
        group.getChildren().add(dealButton);

        Text[] pWagerTexts = new Text[4];
        for (int i = 0; i < pWagerTexts.length; i++) {
            pWagerTexts[i] = new Text();
            pWagerTexts[i].setFont(Font.font("Verdana", 12));
            pWagerTexts[i].setFill(Color.GHOSTWHITE);
            pWagerTexts[i].setX((i * 300) + 155);
            pWagerTexts[i].setY(695);
            pWagerTexts[i].setVisible(false);
        }
        group.getChildren().addAll(pWagerTexts);

        Button[] pYesButtons = new Button[4];
        for (int i = 0; i < pYesButtons.length; i++) {
            pYesButtons[i] = new Button("Yes");
            pYesButtons[i].setTextAlignment(TextAlignment.CENTER);
            pYesButtons[i].setLayoutX((i * 300) + 90);
            pYesButtons[i].setLayoutY(535);
            pYesButtons[i].setPrefWidth(55);
            pYesButtons[i].setVisible(false);
        }
        group.getChildren().addAll(pYesButtons);
        
        Button[] pNoButtons = new Button[4];
        for (int i = 0; i < pNoButtons.length; i++) {
            pNoButtons[i] = new Button("No");
            pNoButtons[i].setTextAlignment(TextAlignment.CENTER);
            pNoButtons[i].setLayoutX((i * 300) + 155);
            pNoButtons[i].setLayoutY(535);
            pNoButtons[i].setPrefWidth(55);
            pNoButtons[i].setVisible(false);
        }
        group.getChildren().addAll(pNoButtons);

        Button[] pHitButtons = new Button[4];
        for (int i = 0; i < pHitButtons.length; i++) {
            pHitButtons[i] = new Button("Hit");
            pHitButtons[i].setTextAlignment(TextAlignment.CENTER);
            pHitButtons[i].setLayoutX((i * 300) + 90);
            pHitButtons[i].setLayoutY(570);
            pHitButtons[i].setPrefWidth(55);
            pHitButtons[i].setVisible(false);
        }
        group.getChildren().addAll(pHitButtons);
        
        Button[] pStandButtons = new Button[4];
        for (int i = 0; i < pStandButtons.length; i++) {
            pStandButtons[i] = new Button("Stand");
            pStandButtons[i].setTextAlignment(TextAlignment.CENTER);
            pStandButtons[i].setLayoutX((i * 300) + 155);
            pStandButtons[i].setLayoutY(570);
            pStandButtons[i].setPrefWidth(55);
            pStandButtons[i].setVisible(false);
        }
        group.getChildren().addAll(pStandButtons);

        Button[] pDoubleButtons = new Button[4];
        for (int i = 0; i < pDoubleButtons.length; i++) {
            pDoubleButtons[i] = new Button("Double");
            pDoubleButtons[i].setTextAlignment(TextAlignment.CENTER);
            pDoubleButtons[i].setLayoutX((i * 300) + 90);
            pDoubleButtons[i].setLayoutY(605);
            pDoubleButtons[i].setPrefWidth(55);
            pDoubleButtons[i].setVisible(false); 
        }
        group.getChildren().addAll(pDoubleButtons);
        
        Button[] pSplitButtons = new Button[4];
        for (int i = 0; i < pSplitButtons.length; i++) {
            pSplitButtons[i] = new Button("Split");
            pSplitButtons[i].setTextAlignment(TextAlignment.CENTER);
            pSplitButtons[i].setLayoutX((i * 300) + 155);
            pSplitButtons[i].setLayoutY(605);
            pSplitButtons[i].setPrefWidth(55);
            pSplitButtons[i].setVisible(false);
        }
        group.getChildren().addAll(pSplitButtons);

        Button[] pSurrenderButtons = new Button[4];
        for (int i = 0; i < pSurrenderButtons.length; i++) {
            pSurrenderButtons[i] = new Button("Surrender");
            pSurrenderButtons[i].setTextAlignment(TextAlignment.CENTER);
            pSurrenderButtons[i].setLayoutX((i * 300) + 90);
            pSurrenderButtons[i].setLayoutY(640);
            pSurrenderButtons[i].setPrefWidth(120);
            pSurrenderButtons[i].setVisible(false);
        }
        group.getChildren().addAll(pSurrenderButtons);

        Button continueButton = new Button("Continue");
        continueButton.setPrefWidth(74);
        continueButton.setPrefHeight(40);
        continueButton.setLayoutX(563);
        continueButton.setLayoutY(580);
        continueButton.setTextAlignment(TextAlignment.CENTER);
        continueButton.setVisible(false);
        group.getChildren().add(continueButton);

        // Start of Button Actions (Note: Can not be in for loops)
        pSubmitButtons[0].setOnAction((event) -> {
            try {
                int IDEntry = Integer.parseInt(pIDFields[0].getText().trim());
                if (IDEntry <= 0) {
                    throw new IllegalArgumentException();
                }

                if (!backEnd.checkIfPlayerExists(IDEntry)) {
                    throw new NoSuchElementException();
                }

                players[0] = backEnd.getPlayer(IDEntry);
                pIDFields[0].setVisible(false);
                pSubmitButtons[0].setVisible(false);

                pWagerFields[0].clear();
                pWagerFields[0].setVisible(true);
                pLeaveButtons[0].setVisible(true);
                pIDAndNameTexts[0].setText("ID: " + IDEntry + "\nName: " + players[0].getName());
                pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                pIDAndNameTexts[0].setVisible(true);
                pCashTexts[0].setVisible(true);
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
                pIDFields[0].clear();
                pIDFields[0].requestFocus();
            }
        });
        pSubmitButtons[1].setOnAction((event) -> {
            try {
                int IDEntry = Integer.parseInt(pIDFields[1].getText().trim());
                if (IDEntry <= 0) {
                    throw new IllegalArgumentException();
                }

                if (!backEnd.checkIfPlayerExists(IDEntry)) {
                    throw new NoSuchElementException();
                }

                players[1] = backEnd.getPlayer(IDEntry);
                pIDFields[1].setVisible(false);
                pSubmitButtons[1].setVisible(false);

                pWagerFields[1].clear();
                pWagerFields[1].setVisible(true);
                pLeaveButtons[1].setVisible(true);
                pIDAndNameTexts[1].setText("ID: " + IDEntry + "\nName: " + players[1].getName());
                pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
                pIDAndNameTexts[1].setVisible(true);
                pCashTexts[1].setVisible(true);
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
                pIDFields[1].clear();
                pIDFields[1].requestFocus();
            }
        });
        pSubmitButtons[2].setOnAction((event) -> {
            try {
                int IDEntry = Integer.parseInt(pIDFields[2].getText().trim());
                if (IDEntry <= 0) {
                    throw new IllegalArgumentException();
                }

                if (!backEnd.checkIfPlayerExists(IDEntry)) {
                    throw new NoSuchElementException();
                }

                players[2] = backEnd.getPlayer(IDEntry);
                pIDFields[2].setVisible(false);
                pSubmitButtons[2].setVisible(false);

                pWagerFields[2].clear();
                pWagerFields[2].setVisible(true);
                pLeaveButtons[2].setVisible(true);
                pIDAndNameTexts[2].setText("ID: " + IDEntry + "\nName: " + players[2].getName());
                pCashTexts[2].setText("Cash: $" + String.format("%.2f", players[2].getCash()));
                pIDAndNameTexts[2].setVisible(true);
                pCashTexts[2].setVisible(true);
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
                pIDFields[2].clear();
                pIDFields[2].requestFocus();
            }
        });
        pSubmitButtons[3].setOnAction((event) -> {
            try {
                int IDEntry = Integer.parseInt(pIDFields[3].getText().trim());
                if (IDEntry <= 0) {
                    throw new IllegalArgumentException();
                }

                if (!backEnd.checkIfPlayerExists(IDEntry)) {
                    throw new NoSuchElementException();
                }

                players[3] = backEnd.getPlayer(IDEntry);
                pIDFields[3].setVisible(false);
                pSubmitButtons[3].setVisible(false);

                pWagerFields[3].clear();
                pWagerFields[3].setVisible(true);
                pLeaveButtons[3].setVisible(true);
                pIDAndNameTexts[3].setText("ID: " + IDEntry + "\nName: " + players[3].getName());
                pCashTexts[3].setText("Cash: $" + String.format("%.2f", players[3].getCash()));
                pIDAndNameTexts[3].setVisible(true);
                pCashTexts[3].setVisible(true);
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
                pIDFields[3].clear();
                pIDFields[3].requestFocus();
            }
        });
        pLeaveButtons[0].setOnAction((event) -> {
            players[0] = null;
            pWagerFields[0].setVisible(false);
            pLeaveButtons[0].setVisible(false);
            pIDAndNameTexts[0].setVisible(false);
            pCashTexts[0].setVisible(false);
            if (players[0] == null && players[1] == null && players[2] == null && players[3] == null) {
                dealButton.setVisible(false);
            }

            pIDFields[0].clear();
            pIDFields[0].setVisible(true);
            pSubmitButtons[0].setVisible(true);
        });
        pLeaveButtons[1].setOnAction((event) -> {
            players[1] = null;
            pWagerFields[1].setVisible(false);
            pLeaveButtons[1].setVisible(false);
            pIDAndNameTexts[1].setVisible(false);
            pCashTexts[1].setVisible(false);
            if (players[0] == null && players[1] == null && players[2] == null && players[3] == null) {
                dealButton.setVisible(false);
            }

            pIDFields[1].clear();
            pIDFields[1].setVisible(true);
            pSubmitButtons[1].setVisible(true);
        });
        pLeaveButtons[2].setOnAction((event) -> {
            players[2] = null;
            pWagerFields[2].setVisible(false);
            pLeaveButtons[2].setVisible(false);
            pIDAndNameTexts[2].setVisible(false);
            pCashTexts[2].setVisible(false);
            if (players[0] == null && players[1] == null && players[2] == null && players[3] == null) {
                dealButton.setVisible(false);
            }

            pIDFields[2].clear();
            pIDFields[2].setVisible(true);
            pSubmitButtons[2].setVisible(true);
        });
        pLeaveButtons[3].setOnAction((event) -> {
            players[3] = null;
            pWagerFields[3].setVisible(false);
            pLeaveButtons[3].setVisible(false);
            pIDAndNameTexts[3].setVisible(false);
            pCashTexts[3].setVisible(false);
            if (players[0] == null && players[1] == null && players[2] == null && players[3] == null) {
                dealButton.setVisible(false);
            }

            pIDFields[3].clear();
            pIDFields[3].setVisible(true);
            pSubmitButtons[3].setVisible(true);
        });

        dealButton.setOnAction((event) -> { // Deal button
            try {
                // Checks for Valid Input
                if (pWagerFields[0].isVisible()) {
                    try {
                        players[0].setWager(0, Integer.parseInt(pWagerFields[0].getText().trim()));
                    } catch (NumberFormatException e) {
                        pWagerFields[0].clear();
                        pWagerFields[0].requestFocus();
                        throw new NumberFormatException(players[0].getName());
                    }
                    if (players[0].getWager(0) <= 0) {
                        pWagerFields[0].clear();
                        pWagerFields[0].requestFocus();
                        throw new IllegalArgumentException(players[0].getName());
                    }
                    if (!backEnd.chargePlayer(players[0], 0, false)) {
                        pWagerFields[0].clear();
                        pWagerFields[0].requestFocus();
                        throw new ArithmeticException(players[0].getName());
                    }
                }
                if (pWagerFields[1].isVisible()) {
                    try {
                        players[1].setWager(1, Integer.parseInt(pWagerFields[1].getText().trim()));
                    } catch (NumberFormatException e) {
                        pWagerFields[1].clear();
                        pWagerFields[1].requestFocus();
                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        throw new NumberFormatException(players[1].getName());
                    }
                    if (players[1].getWager(1) <= 0) {
                        pWagerFields[1].clear();
                        pWagerFields[1].requestFocus();
                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        throw new IllegalArgumentException(players[1].getName());
                    }
                    if (!backEnd.chargePlayer(players[1], 1, false)) {
                        pWagerFields[1].clear();
                        pWagerFields[1].requestFocus();
                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        throw new ArithmeticException(players[1].getName());
                    }
                }
                if (pWagerFields[2].isVisible()) {
                    try {
                        players[2].setWager(2, Integer.parseInt(pWagerFields[2].getText().trim()));
                    } catch (NumberFormatException e) {
                        pWagerFields[2].clear();
                        pWagerFields[2].requestFocus();
                        System.out.println("RAN");
                        

                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        if (players[1] != null && backEnd.payPlayer(players[1], 1, 2)) {
                            pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
                        }
                        throw new NumberFormatException(players[2].getName());
                    }
                    if (players[2].getWager(2) <= 0) {
                        pWagerFields[2].clear();
                        pWagerFields[2].requestFocus();
                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        if (players[1] != null && backEnd.payPlayer(players[1], 1, 2)) {
                            pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
                        }
                        throw new IllegalArgumentException(players[2].getName());
                    }
                    if (!backEnd.chargePlayer(players[2], 2, false)) {
                        pWagerFields[2].clear();
                        pWagerFields[2].requestFocus();
                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        if (players[1] != null && backEnd.payPlayer(players[1], 1, 2)) {
                            pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
                        }
                        throw new ArithmeticException(players[2].getName());
                    }
                }
                if (pWagerFields[3].isVisible()) {
                    try {
                        players[3].setWager(3, Integer.parseInt(pWagerFields[3].getText().trim()));
                    } catch (NumberFormatException e) {
                        pWagerFields[3].clear();
                        pWagerFields[3].requestFocus();
                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        if (players[1] != null && backEnd.payPlayer(players[1], 1, 2)) {
                            pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
                        }
                        if (players[2] != null && backEnd.payPlayer(players[2], 2, 2)) {
                            pCashTexts[2].setText("Cash: $" + String.format("%.2f", players[2].getCash()));
                        }
                        throw new NumberFormatException(players[3].getName());
                    }
                    if (players[3].getWager(3) <= 0) {
                        pWagerFields[3].clear();
                        pWagerFields[3].requestFocus();
                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        if (players[1] != null && backEnd.payPlayer(players[1], 1, 2)) {
                            pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
                        }
                        if (players[2] != null && backEnd.payPlayer(players[2], 2, 2)) {
                            pCashTexts[2].setText("Cash: $" + String.format("%.2f", players[2].getCash()));
                        }
                        throw new IllegalArgumentException(players[3].getName());
                    }
                    if (!backEnd.chargePlayer(players[3], 3, false)) {
                        pWagerFields[3].clear();
                        pWagerFields[3].requestFocus();
                        if (players[0] != null && backEnd.payPlayer(players[0], 0, 2)) {
                            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                        }
                        if (players[1] != null && backEnd.payPlayer(players[1], 1, 2)) {
                            pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
                        }
                        if (players[2] != null && backEnd.payPlayer(players[2], 2, 2)) {
                            pCashTexts[2].setText("Cash: $" + String.format("%.2f", players[2].getCash()));
                        }
                        throw new ArithmeticException(players[3].getName());
                    }
                }
                dealButton.setVisible(false);

                // Blackjack Time!
                newAccountButton.setDisable(true);
                lookupAccountIDButton.setDisable(true);
                manageCashButton.setDisable(true);
                for (int i = 0; i < pIDFields.length; i++) {
                    pIDFields[i].setVisible(false);
                    pSubmitButtons[i].setVisible(false);
                }

                for (int i = 0; i < players.length; i++) {
                    if (players[i] != null) {
                        pWagerFields[i].setVisible(false);
                        pLeaveButtons[i].setVisible(false);
                        pCashTexts[i].setText("Cash: $" + String.format("%.2f", players[i].getCash()));
                        pWagerTexts[i].setText("Wager: $" + players[i].getWager(i));
                        pWagerTexts[i].setVisible(true);

                        pHitButtons[i].setVisible(true);
                        pHitButtons[i].setDisable(true);
                        pStandButtons[i].setVisible(true);
                        pStandButtons[i].setDisable(true);
                        pDoubleButtons[i].setVisible(true);
                        pDoubleButtons[i].setDisable(true);
                        pSplitButtons[i].setVisible(true);
                        pSplitButtons[i].setDisable(true);
                        pSurrenderButtons[i].setVisible(true);
                        pSurrenderButtons[i].setDisable(true);
                    }
                }

                // Deal Starting Cards
                if (players[3] != null) {
                    if (backEnd.hitPlayerHand(players[3], 3, false)) { // TODO REWORK Shuffle Needed. Get rid of if statement check
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(players[3], 3, false, group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                }
                if (players[2] != null) {
                    if (backEnd.hitPlayerHand(players[2], 2, false)) {
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(players[2], 2, false, group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                }
                if (players[1] != null) {
                    if (backEnd.hitPlayerHand(players[1], 1, false)) {
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(players[1], 1, false, group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                }
                if (players[0] != null) {
                    if (backEnd.hitPlayerHand(players[0], 0, false)) {
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(players[0], 0, false, group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                }
                if (backEnd.hitDealer()) {
                    displayCutCard(group);
                }
                inPlayCards.add(displayCard(null, -2, false, group));
                numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                
                if (players[3] != null) {
                    if (backEnd.hitPlayerHand(players[3], 3, false)) {
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(players[3], 3, false, group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                }
                if (players[2] != null) {
                    if (backEnd.hitPlayerHand(players[2], 2, false)) {
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(players[2], 2, false, group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                }
                if (players[1] != null) {
                    if (backEnd.hitPlayerHand(players[1], 1, false)) {
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(players[1], 1, false, group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));
                }
                if (players[0] != null) {
                    if (backEnd.hitPlayerHand(players[0],0, false)) {
                        displayCutCard(group);
                    }
                    inPlayCards.add(displayCard(players[0], 0, false, group));
                    numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards())); 
                }
                if (backEnd.hitDealer()) {
                    displayCutCard(group);
                }
                dealerHiddenCard = displayCard(null, -2, false, group);
                inPlayCards.add(dealerHiddenCard);
                numOfCardsInShoe.setText(Integer.toString(backEnd.getDecks().getNumberOfCards()));

                // Checks to make sure insurance isn't needed and dealer doesn't have Blackjack
                if (backEnd.insuranceNeeded()) { // Insurance (Dealer showing an Ace)
                    // Checks what buttons should be visible and makes sure they are disabled
                    for (int i = players.length - 1; i >= 0; i--) {
                        if (players[i] != null && !backEnd.isPlayerHandBlackjack(players[i], i, false) && backEnd.canAffordInsurance(players[i], i)) {
                            pYesButtons[i].setVisible(true);
                            pNoButtons[i].setVisible(true);
                            pYesButtons[i].setDisable(true);
                            pNoButtons[i].setDisable(true);
                        }
                    }

                    // Finds the first player without Blackjack and can afford insurance enables their buttons
                    if (players[3] != null && !backEnd.isPlayerHandBlackjack(players[3], 3, false) && backEnd.canAffordInsurance(players[3], 3)) {
                        pYesButtons[3].setDisable(false);
                        pNoButtons[3].setDisable(false);
                    } else if (players[2] != null && !backEnd.isPlayerHandBlackjack(players[2], 2, false) && backEnd.canAffordInsurance(players[2], 2)) {
                        pYesButtons[2].setDisable(false);
                        pNoButtons[2].setDisable(false);
                    } else if (players[1] != null && !backEnd.isPlayerHandBlackjack(players[1], 1, false) && backEnd.canAffordInsurance(players[1], 1)) {
                        pYesButtons[1].setDisable(false);
                        pNoButtons[1].setDisable(false);
                    } else if (players[0] != null && !backEnd.isPlayerHandBlackjack(players[0], 0, false) && backEnd.canAffordInsurance(players[0], 0)) {
                        pYesButtons[0].setDisable(false);
                        pNoButtons[0].setDisable(false);
                    } else { // EVERYONE HAS BLACKJACK OR FIXME CAN'T AFFORD IT 
                        showDealerHiddenCard(group);
                        if (backEnd.isDealerBlackjack()) { // TODO FIGURE OUT HOW ROUND IS TERMINATED
                            for (int i = players.length - 1; i >= 0; i--) {
                                if (players[i] != null) { // Push TODO UPDATE TEXT
                                    backEnd.payPlayer(players[i], i, 2);
                                }
                            }
                            // Terminate round
                        } else {
                            for (int i = players.length - 1; i >= 0; i--) {
                                if (players[i] != null) { // Players win TODO UPDATE TEXT, SHOW BLACKJACK CELEBRATION?
                                    backEnd.payPlayer(players[i], i, 3);
                                }
                            }
                        }
                    }
                } else { // No Insurance
                    if (backEnd.isDealerBlackjack()) { // Dealer has Blackjack (Showing Value 10)
                        showDealerHiddenCard(group);
                        for (int i = 0; i < players.length; i++) {
                            if (players[i] != null && backEnd.isPlayerHandBlackjack(players[i], i, false)) {
                                backEnd.payPlayer(players[i], i, 2);
                            } 
                        }
                    } else { // Dealer does not have Blackjack
                        // Determine first player without Blackjack
                        if (players[3] != null && !backEnd.isPlayerHandBlackjack(players[3], 3, false)) {
                            pHitButtons[3].setDisable(false);
                            pStandButtons[3].setDisable(false);
                            pSurrenderButtons[3].setDisable(false);
                            if (backEnd.canDouble(players[3], 3)) {
                                pDoubleButtons[3].setDisable(false);
                            }
                            if (backEnd.canSplit(players[3], 3)) {
                                pSplitButtons[3].setDisable(false);
                            }
                        } else if (players[2] != null && !backEnd.isPlayerHandBlackjack(players[2], 2, false)) {
                            pHitButtons[2].setDisable(false);
                            pStandButtons[2].setDisable(false);
                            pSurrenderButtons[2].setDisable(false);
                            if (backEnd.canDouble(players[2], 2)) {
                                pDoubleButtons[2].setDisable(false);
                            }
                            if (backEnd.canSplit(players[2], 2)) {
                                pSplitButtons[2].setDisable(false);
                            }
                        } else if (players[1] != null && !backEnd.isPlayerHandBlackjack(players[1], 1, false)) {
                            pHitButtons[1].setDisable(false);
                            pStandButtons[1].setDisable(false);
                            pSurrenderButtons[1].setDisable(false);
                            if (backEnd.canDouble(players[1], 1)) {
                                pDoubleButtons[1].setDisable(false);
                            }
                            if (backEnd.canSplit(players[1], 1)) {
                                pSplitButtons[1].setDisable(false);
                            }
                        } else if (players[0] != null && !backEnd.isPlayerHandBlackjack(players[0], 0, false)) {
                            pHitButtons[0].setDisable(false);
                            pStandButtons[0].setDisable(false);
                            pSurrenderButtons[0].setDisable(false);
                            if (backEnd.canDouble(players[0], 0)) {
                                pDoubleButtons[0].setDisable(false);
                            }
                            if (backEnd.canSplit(players[0], 0)) {
                                pSplitButtons[0].setDisable(false);
                            }
                        } else { // Everyone has Blackjack and Dealer does not
                            showDealerHiddenCard(group);

                            for (int i = players.length - 1; i >= 0; i--) {
                                if (players[i] != null) { // Players win TODO UPDATE TEXT? SHOW BLACKJACK CELEBRATION?
                                    backEnd.payPlayer(players[i], i, 3);
                                }
                            }
                        }
                    }
                }

                System.out.println("DEALER HAND: " + backEnd.getDealer().getHand());
                System.out.println("CARD LIST SIZE: " + backEnd.getDecks().getCardList().size());
                System.out.println("USED LIST: " + backEnd.getDecks().getUsedCardList());

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

        pYesButtons[3].setOnAction((event) -> {
            backEnd.chargePlayer(players[3], 3, true);
            pCashTexts[3].setText("Cash: $" + String.format("%.2f", players[3].getCash()));
            pYesButtons[3].setDisable(true);
            pNoButtons[3].setDisable(true);

            if (players[2] != null && !backEnd.isPlayerHandBlackjack(players[2], 2, false) && backEnd.canAffordInsurance(players[2], 2)) {
                pYesButtons[2].setDisable(false);
                pNoButtons[2].setDisable(false);
            } else if (players[1] != null && !backEnd.isPlayerHandBlackjack(players[1], 1, false) && backEnd.canAffordInsurance(players[1], 1)) {
                pYesButtons[1].setDisable(false);
                pNoButtons[1].setDisable(false);
            } else if (players[0] != null && !backEnd.isPlayerHandBlackjack(players[0], 0, false) && backEnd.canAffordInsurance(players[0], 0)) {
                pYesButtons[0].setDisable(false);
                pNoButtons[0].setDisable(false);
            } else {
                pYesButtons[3].setVisible(false);
                pNoButtons[3].setVisible(false);

                if (backEnd.isDealerBlackjack()) {
                    showDealerHiddenCard(group);
                    backEnd.payPlayer(players[3], 3, 4);
                    pCashTexts[3].setText("Cash: $" + String.format("%.2f", players[3].getCash()));
                    continueButton.setDisable(false);
                    continueButton.setVisible(true);
                } else {
                    // FIXME: Buttons not appearing correctly
                    pHitButtons[3].setDisable(false);
                    pStandButtons[3].setDisable(false);
                    if (backEnd.canDouble(players[3], 3)) {
                        pDoubleButtons[3].setDisable(false);
                    }
                    if (backEnd.canSplit(players[3], 3)) {
                        pSplitButtons[3].setDisable(false);
                    }
                    pSurrenderButtons[3].setDisable(false);
                }
            }
        });
        pYesButtons[2].setOnAction((event) -> {
            backEnd.chargePlayer(players[2], 2, true);
            pCashTexts[2].setText("Cash: $" + String.format("%.2f", players[2].getCash()));
            pYesButtons[2].setDisable(true);
            pNoButtons[2].setDisable(true);

            if (players[1] != null && !backEnd.isPlayerHandBlackjack(players[1], 1, false) && backEnd.canAffordInsurance(players[1], 1)) {
                pYesButtons[1].setDisable(false);
                pNoButtons[1].setDisable(false);
            } else if (players[0] != null && !backEnd.isPlayerHandBlackjack(players[0], 0, false) && backEnd.canAffordInsurance(players[0], 0)) {
                pYesButtons[0].setDisable(false);
                pNoButtons[0].setDisable(false);
            } else {
                pYesButtons[2].setVisible(false);
                pNoButtons[2].setVisible(false);

                if (backEnd.isDealerBlackjack()) {
                    showDealerHiddenCard(group);
                    backEnd.payPlayer(players[2], 2, 4);
                    pCashTexts[2].setText("Cash: $" + String.format("%.2f", players[2].getCash()));
                    continueButton.setDisable(false);
                    continueButton.setVisible(true);
                } else {
                    // FIXME: Buttons not appearing correctly
                    pHitButtons[2].setDisable(false);
                    pStandButtons[2].setDisable(false);
                    if (backEnd.canDouble(players[2], 2)) {
                        pDoubleButtons[2].setDisable(false);
                    }
                    if (backEnd.canSplit(players[2], 2)) {
                        pSplitButtons[2].setDisable(false);
                    }
                    pSurrenderButtons[2].setDisable(false);
                }
            }
        });
        pYesButtons[1].setOnAction((event) -> {
            backEnd.chargePlayer(players[1], 1, true);
            pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
            pYesButtons[1].setDisable(true);
            pNoButtons[1].setDisable(true);

            if (players[0] != null && !backEnd.isPlayerHandBlackjack(players[0], 0, false) && backEnd.canAffordInsurance(players[0], 0)) {
                pYesButtons[0].setDisable(false);
                pNoButtons[0].setDisable(false);
            } else {
                pYesButtons[1].setVisible(false);
                pNoButtons[1].setVisible(false);

                if (backEnd.isDealerBlackjack()) {
                    showDealerHiddenCard(group);
                    backEnd.payPlayer(players[1], 1, 4);
                    pCashTexts[1].setText("Cash: $" + String.format("%.2f", players[1].getCash()));
                    continueButton.setDisable(false);
                    continueButton.setVisible(true);
                } else {
                    // FIXME: Buttons not appearing correctly
                    pHitButtons[1].setDisable(false);
                    pStandButtons[1].setDisable(false);
                    if (backEnd.canDouble(players[1], 1)) {
                        pDoubleButtons[1].setDisable(false);
                    }
                    if (backEnd.canSplit(players[1], 1)) {
                        pSplitButtons[1].setDisable(false);
                    }
                    pSurrenderButtons[1].setDisable(false);
                }
            }
        });
        pYesButtons[0].setOnAction((event) -> {
            backEnd.chargePlayer(players[0], 0, true);
            pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
            pYesButtons[0].setDisable(true);
            pNoButtons[0].setDisable(true);

            pYesButtons[0].setVisible(false);
            pNoButtons[0].setVisible(false);
            if (backEnd.isDealerBlackjack()) {
                showDealerHiddenCard(group);
                backEnd.payPlayer(players[0], 0, 4);
                pCashTexts[0].setText("Cash: $" + String.format("%.2f", players[0].getCash()));
                continueButton.setDisable(false);
                continueButton.setVisible(true);
            } else {
                // FIXME: Buttons not appearing correctly
                pHitButtons[0].setDisable(false);
                pStandButtons[0].setDisable(false);
                if (backEnd.canDouble(players[0], 0)) {
                    pDoubleButtons[0].setDisable(false);
                }
                if (backEnd.canSplit(players[0], 0)) {
                    pSplitButtons[0].setDisable(false);
                }
                pSurrenderButtons[0].setDisable(false);
            }
        });

        pNoButtons[3].setOnAction((event) -> {
            pYesButtons[3].setDisable(true);
            pNoButtons[3].setDisable(true);

            if (players[2] != null && !backEnd.isPlayerHandBlackjack(players[2], 2, false) && backEnd.canAffordInsurance(players[2], 2)) {
                pYesButtons[2].setDisable(false);
                pNoButtons[2].setDisable(false);
            } else if (players[1] != null && !backEnd.isPlayerHandBlackjack(players[1], 1, false) && backEnd.canAffordInsurance(players[1], 1)) {
                pYesButtons[1].setDisable(false);
                pNoButtons[1].setDisable(false);
            } else if (players[0] != null && !backEnd.isPlayerHandBlackjack(players[0], 0, false) && backEnd.canAffordInsurance(players[0], 0)) {
                pYesButtons[0].setDisable(false);
                pNoButtons[0].setDisable(false);
            } else {
                if (backEnd.isDealerBlackjack()) {
                    
                } else {
                    
                }
            }
        });
        pNoButtons[2].setOnAction((event) -> {
            pYesButtons[2].setDisable(true);
            pNoButtons[2].setDisable(true);
            
            if (players[1] != null && !backEnd.isPlayerHandBlackjack(players[1], 1, false) && backEnd.canAffordInsurance(players[1], 1)) {
                pYesButtons[1].setDisable(false);
                pNoButtons[1].setDisable(false);
            } else if (players[0] != null && !backEnd.isPlayerHandBlackjack(players[0], 0, false) && backEnd.canAffordInsurance(players[0], 0)) {
                pYesButtons[0].setDisable(false);
                pNoButtons[0].setDisable(false);
            } else {
                if (backEnd.isDealerBlackjack()) {
                    
                } else {
                    
                }
            }
        });
        pNoButtons[1].setOnAction((event) -> {
            pYesButtons[1].setDisable(true);
            pNoButtons[1].setDisable(true);

            if (players[0] != null && !backEnd.isPlayerHandBlackjack(players[0], 0, false) && backEnd.canAffordInsurance(players[0], 0)) {
                pYesButtons[0].setDisable(false);
                pNoButtons[0].setDisable(false);
            } else {
                if (backEnd.isDealerBlackjack()) {
                    
                } else {
                    
                }
            }
        });
        pNoButtons[0].setOnAction((event) -> {
            pYesButtons[0].setDisable(true);
            pNoButtons[0].setDisable(true);
            
            if (backEnd.isDealerBlackjack()) {
                    
            } else {
                
            }
        });
        

        pHitButtons[0].setOnAction((event) -> {

        });
        pHitButtons[1].setOnAction((event) -> {
            
        });
        pHitButtons[2].setOnAction((event) -> {
            
        });
        pHitButtons[3].setOnAction((event) -> {
            
        });
        pStandButtons[0].setOnAction((event) -> {

        });
        pStandButtons[1].setOnAction((event) -> {

        });
        pStandButtons[2].setOnAction((event) -> {

        });
        pStandButtons[3].setOnAction((event) -> {

        });
        
        pDoubleButtons[0].setOnAction((event) -> {

        });
        pDoubleButtons[1].setOnAction((event) -> {

        });
        pDoubleButtons[2].setOnAction((event) -> {

        });
        pDoubleButtons[3].setOnAction((event) -> {

        });     
        pSplitButtons[0].setOnAction((event) -> {

        });
        pSplitButtons[1].setOnAction((event) -> {

        });
        pSplitButtons[2].setOnAction((event) -> {

        });
        pSplitButtons[3].setOnAction((event) -> {

        });
        pSurrenderButtons[0].setOnAction((event) -> {

        });
        pSurrenderButtons[1].setOnAction((event) -> {

        });
        pSurrenderButtons[2].setOnAction((event) -> {

        });
        pSurrenderButtons[3].setOnAction((event) -> {

        });

        continueButton.setOnAction((event) -> {
            resetTable();
        });

        mStage.show();
    }


    /**
     * Function that clears the table of all cards, resets all necessary values, and brings the
     * game back wager screen
     */
    private void resetTable() {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                players[i].clearHands();
                players[i].clearWagers();
            }
        }

        if (backEnd.isShuffleNeeded()) {
            backEnd.setShuffleNeeded(false);
        }
    }

    /**
     * 
     * 
     * @param playerID ID of the player to display the card to, use -2 for the dealer
     * @param playerIndex Hand Index to display the card to, used -2 for the dealer
     * @param group The group for the front end
     * @return The ImageView of the card in children list
     */
    private ImageView displayCard(Player player, int playerIndex, boolean isSplitHand, Group group) {
        ArrayList<Card> handCardList;
        int cardXPos;
        int cardYPos;

        if (player == null && playerIndex == -2) {
            handCardList = backEnd.getDealer().getHand().getCardList();
            cardXPos = Constants.dealerCardStartingXPos + ((handCardList.size() - 1) * Constants.layeredCardOffset);
            cardYPos = Constants.dealerCardFrontYPos;
        } else {
            handCardList = player.getHand(playerIndex, isSplitHand).getCardList();

            if (playerIndex == 3 && !isSplitHand) {
                cardXPos = Constants.p4CardStartingXPos + ((handCardList.size() - 1) * Constants.layeredCardOffset);
            } else if (playerIndex == 2 && !isSplitHand) {
                cardXPos = Constants.p3CardStartingXPos + ((handCardList.size() - 1) * Constants.layeredCardOffset);
            } else if (playerIndex == 1 && !isSplitHand) {
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
