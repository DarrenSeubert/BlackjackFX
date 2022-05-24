import java.util.List;

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
     * 
     * 
     * @param dataManager
     */
    public BackEnd(DataManager dataManager) {
        dealer = new Dealer();
        this.dm = dataManager;
    }

    /**
     * 
     * 
     * @return
     */
    public Decks getDecks() {
        return decks;
    }

    /**
     * 
     * 
     * @return
     */
    public DataManager getDm() {
        return dm;
    }

    /**
     * 
     * 
     * @return
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * 
     * 
     * @param numberOfDecks
     */
    public void createDeck(int numberOfDecks) {
        decks = new Decks(numberOfDecks);
    }

    /**
     * 
     * 
     * @param playerID
     * @return
     */
    public boolean checkIfPlayerExists(int playerID) {
        if (dm.playerTable.get(playerID) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * 
     * @param playerID
     * @param cashAmount
     * @return
     */
    public boolean addOrSubtractCashToPlayer(int playerID, double cashAmount) {
        if (!checkIfPlayerExists(playerID) || cashAmount == 0 || (cashAmount < 0 && dm.playerTable.get(playerID).getCash() + cashAmount < 0)) {
            return false;
        } else {
            Player updatedPlayer = new Player(playerID, dm.playerTable.get(playerID).getName(), dm.playerTable.get(playerID).getCash() + cashAmount);
            dm.playerTable.put(playerID, updatedPlayer);
            dm.updateExistingPlayerInPlayerFile(playerID, dm.playerTable.get(playerID).getCash());
            return true;
        }
    }

    /**
     * 
     * 
     * @param name
     * @param cash
     */
    public void addNewPlayerToGame(String name, double cash) {
        dm.incrementLargestIDNumber();
        Player newPlayer = new Player(dm.getLargestIDNumber(), name, cash);
        dm.playerTable.put(newPlayer.getIDNumber(), newPlayer);
        dm.writeNewPlayerToFile(newPlayer);
    }

    /**
     * 
     * 
     * @param playerID
     * @param handIndex
     * @return True if reshuffle is needed, else false
     */
    public boolean hitPlayer(int playerID, int handIndex) {
        if (decks.getCardList().get(0).getSuit().equals(Card.Suit.Cut)) { // Checks if cut card is to be dealt
            decks.getUsedCardList().add(decks.getCardList().remove(0)); // TODO Maybe move adding card to used to when clear hand is called
            dm.playerTable.get(playerID).getHands().get(handIndex).getCardList().add(decks.getCardList().get(0));
            decks.getUsedCardList().add(decks.getCardList().remove(0)); // TODO Maybe move adding card to used to when clear hand is called
            return true;
        } else {
            dm.playerTable.get(playerID).getHands().get(handIndex).getCardList().add(decks.getCardList().get(0));
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
    public boolean isPlayerHandBust(int playerID, int handIndex) {
         if (dm.playerTable.get(playerID).getHands().get(handIndex).getPossibleHandValues().size() == 0) {
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
    public boolean possibleDealerBlackjack() {
        if (dealer.getHand().getCardList().get(1).getValue() == Card.Value.Ace) {
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
    public boolean isPlayerBlackjack(int playerID, int handIndex) {
        List<Hand> playerHands = dm.playerTable.get(playerID).getHands();

        if (playerHands.get(handIndex).getCardList().size() != 2) {
            return false;
        } else {
            for (int i = 0; i < playerHands.get(handIndex).getPossibleHandValues().size(); i++) {
                if (playerHands.get(handIndex).getPossibleHandValues().get(i) == 21) {
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
    public boolean canSplit(int playerID, double wager, int handIndex) {
        Player player = dm.playerTable.get(playerID);

        if (player.getHands().get(handIndex).getCardList().get(0).getValue() == 
            player.getHands().get(handIndex).getCardList().get(1).getValue() && 
            player.getCash() >= wager) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * 
     * @param playerID
     * @param wager
     * @return
     */
    public boolean canDouble(int playerID, double wager) {
        if (dm.playerTable.get(playerID).getCash() >= wager) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that pays a player based on the option passed in
     * 
     * @param playerID The ID of the player to edit
     * @param wager Original wager amount by the player
     * @param option Option that determines how much the player is paid
     *               1: Win
     *               2: Push
     *               3: Blackjack
     *               4: Surrender
     * @return True on success, else false
     */
    public boolean payPlayer(int playerID, double wager, int option) {
        if (option == 1) {
            return addOrSubtractCashToPlayer(playerID, wager * 2);
        } else if (option == 2) {
            return addOrSubtractCashToPlayer(playerID, wager);
        } else if (option == 3) {
            return addOrSubtractCashToPlayer(playerID, wager * 2.5);
        } else if (option == 4) {
            return addOrSubtractCashToPlayer(playerID, wager * 0.5);
        } else {
            return false;
        }
    }
}
