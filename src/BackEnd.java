import java.util.NoSuchElementException;

/**
 * Class that handles the back end engine for Blackjack
 * 
 * @author Darren Seubert
 */
public class BackEnd {
    private Dealer dealer;
    private Decks decks;
    private DataManager dm;

    /**
     * Constructor for the BackEnd
     * 
     * @param dataManager The Instance of the Data Manager
     */
    public BackEnd(DataManager dataManager) {
        dealer = new Dealer();
        this.dm = dataManager;
    }

    /**
     * Getter Method for the Decks of Cards
     * 
     * @return The Decks of Cards
     */
    public Decks getDecks() {
        return decks;
    }

    /**
     * Getter method for the Data Manager
     * 
     * @return The Data Manager
     */
    public DataManager getDm() {
        return dm;
    }

    /**
     * A method that returns the Player
     * 
     * @param playerID The ID of the Player to Lookup
     * @throws NoSuchElementException If the Player does not exist
     * @return The Player with the Given Player ID
     */
    public Player getPlayer(int playerID) {
        if (!checkIfPlayerExists(playerID)) {
            throw new NoSuchElementException("Error: Player Does Not Exist");
        }

        return dm.playerTable.get(playerID);
    }

    /**
     * A getter method for the Dealer
     * 
     * @return The Dealer
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * A method that creates a new decks object
     * 
     * @param numberOfDecks
     */
    public void createDecks(int numberOfDecks) {
        decks = new Decks(numberOfDecks);
    }

    /**
     * 
     * 
     * @param playerID
     * @return
     */
    public boolean checkIfPlayerExists(int playerID) {
        return dm.playerTable.get(playerID) != null;
    }

    /**
     * 
     * 
     * @param playerID
     * @param cashAmount
     * @return
     */
    // TODO Maybe separate out this method into two: 
    // 1) For known player already exits (For in game paying)
    // 2) For not known if player already exits (For adding money to an account pre game)
    public boolean addOrSubtractCashToPlayer(int playerID, double cashAmount) {
        if (!checkIfPlayerExists(playerID) || cashAmount == 0 || (cashAmount < 0 && dm.playerTable.get(playerID).getCash() + cashAmount < 0)) {
            return false;
        } else {
            Player updatedPlayer = new Player(playerID, dm.playerTable.get(playerID).getName(), dm.playerTable.get(playerID).getCash() + cashAmount);
            dm.playerTable.put(playerID, updatedPlayer);
            dm.updateExistingPlayerInPlayersFile(playerID);
            return true;
        }
    }

    /**
     * A Method that Adds a New Player to the DataManger
     * 
     * @param name The Name of the Player to Add
     * @param cash The Amount of Cash for the Player
     */
    public void addNewPlayerToGame(String name, double cash) {
        dm.incrementLargestIDNumber();
        Player newPlayer = new Player(dm.getLargestIDNumber(), name, cash);
        dm.playerTable.put(newPlayer.getID(), newPlayer);
        dm.writeNewPlayerToFile(newPlayer);
    }

    /**
     * 
     * 
     * @param hand Hand of player to hit
     * @return True if reshuffle is needed, else false
     */
    public boolean hitPlayerHand(Player player, int playerIndex, boolean isSplitHand) {
        if (decks.getCardList().get(0).getSuit().equals(Card.Suit.Cut)) { // Checks if cut card is to be dealt
            decks.getCardList().remove(0);
            decks.setCutCardInDeck(false);
            player.addCard(playerIndex, isSplitHand, decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0)); // TODO Maybe move adding card to used to when clear hand is called
            return true;
        } else {
            player.addCard(playerIndex, isSplitHand, decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0)); // TODO Maybe move adding card to used to when clear hand is called
            return false;
        }
    }

    /**
     * 
     * 
     * @return True if reshuffle is needed, else false
     */
    public boolean hitDealer() {
        if (decks.getCardList().get(0).getSuit().equals(Card.Suit.Cut)) { // Checks if cut card is to be dealt
            decks.getUsedCardList().add(decks.getCardList().remove(0)); // TODO Maybe move adding card to used to when clear hand is called
            dealer.getHand().getCardList().add(decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0)); // TODO Maybe move adding card to used to when clear hand is called
            return true;
        } else {
            dealer.getHand().getCardList().add(decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0)); // TODO Maybe move adding card to used to when clear hand is called
            return false;
        }
    }

    /**
     * 
     * 
     * @param playerID
     * @param handIndex
     * @return True is hand is a bust, else false
     */
    public boolean isPlayerHandBust(Hand hand) {
         if (hand.getPossibleHandValues().size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * 
     * @return True is hand is a bust, else false
     */
    public boolean isDealerHandBust() {
        if (dealer.getHand().getPossibleHandValues().size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * 
     * @return
     */
    public boolean insuranceNeeded() {
        if (dealer.getHand().getCardList().get(0).getValue() == Card.Value.Ace) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * 
     * @return True if dealer has Blackjack, else false
     */
    public boolean isDealerBlackjack() {
        if (dealer.getHand().getCardList().size() != 2) {
            return false;
        } else {
            for (int i = 0; i < dealer.getHand().getPossibleHandValues().size(); i++) {
                if (dealer.getHand().getPossibleHandValues().get(i) == 21) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 
     * 
     * @param playerID
     * @return True if given player has Blackjack, else false
     */
    public boolean isPlayerHandBlackjack(Hand hand) {
        if (hand.getCardList().size() != 2) {
            return false;
        } else {
            for (int i = 0; i < hand.getPossibleHandValues().size(); i++) {
                if (hand.getPossibleHandValues().get(i) == 21) {
                    return true;
                }
            }
    
            return false;
        }
    }

    /**
     * 
     * 
     * @param playerID
     * @param wager
     * @param handIndex
     * @return
     */
    public boolean canSplit(Player player, int playerIndex) {
        return player.getHand(playerIndex, false).getCardList().get(0).getValue() == player.getHand(playerIndex, false).getCardList().get(1).getValue() 
            && player.getCash() >= player.getWager(playerIndex);
    }

    /**
     * 
     * 
     * @param playerID
     * @param wager
     * @return
     */
    public boolean canDouble(Player player, int playerIndex) {
        return player.getCash() >= player.getWager(playerIndex);
    }

    /**
     * 
     * 
     * @param playerID
     * @param originalWager
     * @return
     */
    public boolean canAffordInsurance(Player player, int playerIndex) {
        return player.getCash() >= player.getWager(playerIndex) / 2.0;
    }

    /** Method that pays a player based on the option passed in
     * 
     * @param playerID The ID of the player to edit
     * @param wager Original wager amount by the player
     * @param option Option that determines how much the player is paid <p>
     *               1: Win <p>
     *               2: Push <p>
     *               3: Blackjack <p>
     *               4: Surrender <p>
     * @return True on success, else false
     */
    public boolean payPlayer(Player player, int playerIndex, int option) {
        if (option == 1) {
            return payPlayerHelper(player, player.getWager(playerIndex) * 2);
        } else if (option == 2) {
            return payPlayerHelper(player, player.getWager(playerIndex));
        } else if (option == 3) {
            return payPlayerHelper(player, player.getWager(playerIndex) * 2.5);
        } else if (option == 4) {
            return payPlayerHelper(player, player.getWager(playerIndex) * 0.5);
        } else {
            return false;
        }
    }

    /**
     * 
     * 
     * @param player
     * @param cashAmount
     * @return
     */
    private boolean payPlayerHelper(Player player, double cashAmount) {
        if (cashAmount == 0 || (cashAmount < 0 && player.getCash() + cashAmount < 0)) {
            return false;
        } else {
            player.setCash(player.getCash() + cashAmount, dm);
            dm.playerTable.put(player.getID(), player);
            dm.updateExistingPlayerInPlayersFile(player.getID());
            return true;
        }
    }
}
