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
    public boolean addOrSubtractCashToPlayer(int playerID, int cashAmount) {
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
    public void addNewPlayerToGame(String name, int cash) {
        dm.incrementLargestIDNumber();
        Player newPlayer = new Player(dm.getLargestIDNumber(), name, cash);
        dm.playerTable.put(newPlayer.getIDNumber(), newPlayer);
        dm.writeNewPlayerToFile(newPlayer);
    }

    /**
     * 
     * 
     * @param playerID
     * @return True if player's hand is bust, else false
     */
    public boolean hitPlayer(int playerID, int handIndex) {
        dm.playerTable.get(playerID).getHands().get(handIndex).getCardList().add(decks.getCardList().get(0));
        decks.getCardList().remove(0);

        if (dm.playerTable.get(playerID).getHands().get(handIndex).getPossibleHandValues().size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * 
     * @return True if dealer's hand is bust, else false
     */
    public boolean hitDealer() {
        dealer.getHand().getCardList().add(decks.getCardList().get(0));
        decks.getCardList().remove(0);

        if (dealer.getHand().getCardList().size() == 0) {
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

    public boolean canSplit(int playerID, int handIndex) {
        List<Hand> playerHands = dm.playerTable.get(playerID).getHands();

        if (playerHands.get(handIndex).getCardList().get(0).getValue() == playerHands.get(handIndex).getCardList().get(1).getValue()) {
            return true;
        } else {
            return false;
        }
    }

}
