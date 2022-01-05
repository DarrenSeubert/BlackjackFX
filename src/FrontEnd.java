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
        Text numOfCardsInShoe = new Text(Integer.toString(backEnd.getDecks().getNumberOfCards()));
        numOfCardsInShoe.setFont(Font.font("Verdana", 13));
        numOfCardsInShoe.setFill(Color.GHOSTWHITE);
        numOfCardsInShoe.setX(1078);
        numOfCardsInShoe.setY(155);
        group.getChildren().addAll(shoePile, numOfCardsInShoe);

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

        Button dealButton = new Button("DEAL");
        dealButton.setFont(new Font(20));
        dealButton.setPrefWidth(74);
        dealButton.setPrefHeight(40);
        dealButton.setLayoutX(563);
        dealButton.setLayoutY(580);
        dealButton.setTextAlignment(TextAlignment.CENTER);
        dealButton.setVisible(false);
        group.getChildren().addAll(newAccountButton, lookupAccountIDButton, manageCashButton, dealButton);

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

        // Text numOfCardsInShoe = new Text(Integer.toString(backEnd.getDecks().getNumberOfCards()));
        // numOfCardsInShoe.setFont(Font.font("Verdana", 13));
        // numOfCardsInShoe.setFill(Color.GHOSTWHITE);
        // numOfCardsInShoe.setX(1078);
        // numOfCardsInShoe.setY(155);

        TextField p1WagerBox = new TextField();
        p1WagerBox.setPromptText("Wager $");
        p1WagerBox.setPrefWidth(70);
        p1WagerBox.setLayoutX(115);
        p1WagerBox.setLayoutY(570);
        p1WagerBox.setVisible(false);
        TextField p2WagerBox = new TextField();
        p2WagerBox.setPromptText("Wager $");
        p2WagerBox.setPrefWidth(70);
        p2WagerBox.setLayoutX(415);
        p2WagerBox.setLayoutY(570);
        p2WagerBox.setVisible(false);
        TextField p3WagerBox = new TextField();
        p3WagerBox.setPromptText("Wager $");
        p3WagerBox.setPrefWidth(70);
        p3WagerBox.setLayoutX(715);
        p3WagerBox.setLayoutY(570);
        p3WagerBox.setVisible(false);
        TextField p4WagerBox = new TextField();
        p4WagerBox.setPromptText("Wager $");
        p4WagerBox.setPrefWidth(70);
        p4WagerBox.setLayoutX(1015);
        p4WagerBox.setLayoutY(570);
        p4WagerBox.setVisible(false);
        group.getChildren().addAll(p1WagerBox, p2WagerBox, p3WagerBox, p4WagerBox);

        Button p1LeaveButton = new Button("Leave");
        p1LeaveButton.setTextAlignment(TextAlignment.CENTER);
        p1LeaveButton.setLayoutX(115);
        p1LeaveButton.setLayoutY(605);
        p1LeaveButton.setPrefWidth(70);
        p1LeaveButton.setVisible(false);
        p1LeaveButton.setOnAction((event) -> {
            p1ID = -1;
            p1WagerBox.setVisible(false);
            p1LeaveButton.setVisible(false);

            if (p1ID == -1 && p2ID == -1 && p3ID == -1 && p4ID == -1) {
                dealButton.setVisible(false);
            }
        });

        Button p2LeaveButton = new Button("Leave");
        p2LeaveButton.setTextAlignment(TextAlignment.CENTER);
        p2LeaveButton.setLayoutX(115);
        p2LeaveButton.setLayoutY(605);
        p2LeaveButton.setPrefWidth(70);
        p2LeaveButton.setVisible(false);
        Button p3LeaveButton = new Button("Leave");
        p3LeaveButton.setTextAlignment(TextAlignment.CENTER);
        p3LeaveButton.setLayoutX(115);
        p3LeaveButton.setLayoutY(605);
        p3LeaveButton.setPrefWidth(70);
        p3LeaveButton.setVisible(false);
        Button p4LeaveButton = new Button("Leave");
        p4LeaveButton.setTextAlignment(TextAlignment.CENTER);
        p4LeaveButton.setLayoutX(115);
        p4LeaveButton.setLayoutY(605);
        p4LeaveButton.setPrefWidth(70);
        p4LeaveButton.setVisible(false);
        group.getChildren().addAll(p1LeaveButton, p2LeaveButton, p3LeaveButton, p4LeaveButton);

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
                p1WagerBox.setVisible(true);
                p1LeaveButton.setVisible(true);
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
            }
        });

        Button p2SubmitButton = new Button("Submit");
        p2SubmitButton.setTextAlignment(TextAlignment.CENTER);
        p2SubmitButton.setLayoutX(415);
        p2SubmitButton.setLayoutY(605);
        p2SubmitButton.setPrefWidth(70);
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
                // START LAYOUT
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
            }
        });

        Button p3SubmitButton = new Button("Submit");
        p3SubmitButton.setTextAlignment(TextAlignment.CENTER);
        p3SubmitButton.setLayoutX(715);
        p3SubmitButton.setLayoutY(605);
        p3SubmitButton.setPrefWidth(70);
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
                // START LAYOUT
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
            }
        });

        Button p4SubmitButton = new Button("Submit");
        p4SubmitButton.setTextAlignment(TextAlignment.CENTER);
        p4SubmitButton.setLayoutX(1015);
        p4SubmitButton.setLayoutY(605);
        p4SubmitButton.setPrefWidth(70);
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
                // START LAYOUT
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
            }
        });
        group.getChildren().addAll(p1SubmitButton, p2SubmitButton, p3SubmitButton, p4SubmitButton);
        
        ImageView discardPile = new ImageView(Constants.backOfCardImage);
        discardPile.setX(65);
        discardPile.setY(25);
        discardPile.setVisible(false);
        ImageView cutCard = new ImageView(Constants.cutCardImage);
        cutCard.setX(Constants.shoePileXPos);
        cutCard.setY(Constants.shoePileYPos);
        cutCard.setVisible(false);
        group.getChildren().addAll(discardPile, cutCard);

        mStage.show();
    }
}
