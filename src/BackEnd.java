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

        Player player = dm.playerTable.get(playerID);
        player.clearHands();
        player.clearWagers();

        return player;
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
     * @param lookupName
     * @return
     */
    public int[] lookupPlayerID(String lookupName) {
        return dm.lookupPlayerID(lookupName);
    }

    /**
     * 
     * 
     * @return
     */
    public int getLargestDmIDNumber() {
        return dm.getLargestIDNumber();
    }

    /**
     * 
     * 
     * @param playerID
     * @param cashAmount
     * @return
     */
    public boolean manageCash(int playerID, double cashAmount) {
        if (!checkIfPlayerExists(playerID) || cashAmount == 0 || (cashAmount < 0 && dm.playerTable.get(playerID).getCash() + cashAmount < 0)) {
            return false;
        } else {
            Player player = new Player(playerID, dm.playerTable.get(playerID).getName(), dm.playerTable.get(playerID).getCash() + cashAmount);
            dm.playerTable.put(playerID, player);
            dm.updateExistingPlayerInPlayersFile(player);
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
     * @param player
     * @param playerIndex
     * @param isSplitHand
     * @return True if reshuffle is needed, else false
     */
    public boolean hitPlayerHand(Player player, int playerIndex, boolean isSplitHand) {
        if (decks.getCardList().get(0).getSuit().equals(Card.Suit.Cut)) { // Checks if cut card is to be dealt
            decks.getCardList().remove(0);
            decks.setCutCardInDeck(false);
            player.addCard(playerIndex, isSplitHand, decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0));
            return true;
        } else {
            player.addCard(playerIndex, isSplitHand, decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0));
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
            decks.getUsedCardList().add(decks.getCardList().remove(0));
            dealer.getHand().getCardList().add(decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0));
            return true;
        } else {
            dealer.getHand().getCardList().add(decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0));
            return false;
        }
    }

    /**
     * 
     * 
     * @param player
     * @param handIndex
     * @param isSplitHand
     * @return True is hand is a bust, else false
     */
    public boolean isPlayerHandBust(Player player, int handIndex, boolean isSplitHand) {
        Hand hand = player.getHand(handIndex, isSplitHand);
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
     * @param player
     * @param playerIndex
     * @param isSplitHand
     * @return True if given player has Blackjack, else false
     */
    public boolean isPlayerHandBlackjack(Player player, int playerIndex, boolean isSplitHand) {
        Hand hand = player.getHand(playerIndex, isSplitHand);
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
     * @param playerIndex
     * @return
     */
    public boolean canSplit(Player player, int playerIndex) {
        return player.getHand(playerIndex, false).getCardList().get(0).getValueInt() == player.getHand(playerIndex, false).getCardList().get(1).getValueInt() 
            && player.getCash() >= player.getWager(playerIndex);
    }

    /**
     * 
     * 
     * @param player
     * @param playerIndex
     * @return
     */
    public boolean canDouble(Player player, int playerIndex) {
        return player.getCash() >= player.getWager(playerIndex);
    }

    /**
     * 
     * 
     * @param player
     * @param playerIndex
     * @return
     */
    public boolean canAffordInsurance(Player player, int playerIndex) {
        return player.getCash() >= player.getWager(playerIndex) / 2.0;
    }

    /**
     * Note: Wager must be in player's wagers array before calling this method
     * 
     * @param player
     * @param playerIndex
     * @param insurance
     * @return
     */
    public boolean chargePlayer(Player player, int playerIndex, boolean insurance) {
        double wager = player.getWager(playerIndex);
        if (insurance) {
            wager /= 2.0;
        }

        if (!checkIfPlayerExists(player.getID()) || (player.getCash() - wager < 0)) {
            return false;
        } else {
            player.addOrSubtractCash(-wager);
            dm.playerTable.put(player.getID(), player);
            dm.updateExistingPlayerInPlayersFile(player);
            return true;
        }
    }

    /** Method that pays a player based on the option passed in
     * 
     * @param player The player to be paid
     * @param playerIndex The index of the player
     * @param option Option that determines how much the player is paid <p>
     *               1: Win <p>
     *               2: Push <p>
     *               3: Blackjack <p>
     *               4: Insurance <p>
     *               5: Surrender <p>
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
            return payPlayerHelper(player, player.getWager(playerIndex) * 1.5);
        } else if (option == 5) {
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
            player.addOrSubtractCash(cashAmount);
            dm.playerTable.put(player.getID(), player);
            dm.updateExistingPlayerInPlayersFile(player);
            return true;
        }
    }
}
