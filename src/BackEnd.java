/**
 * Class that handles the back end engine for Blackjack
 * 
 * @author Darren Seubert
 */
public class BackEnd {
    private Dealer dealer;
    private Decks cards;
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
     * @param numberOfDecks
     */
    public void createDeck(int numberOfDecks) {
        cards = new Decks(numberOfDecks);
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
     * @param cash
     */
    public void updateExistingPlayerInGame(int playerID, int cash) {
        Player updatedPlayer = new Player(playerID, dm.playerTable.get(playerID).getName(), cash);

        dm.playerTable.put(playerID, updatedPlayer);
        dm.updateExistingPlayerInPlayerFile(playerID, cash);
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
    public boolean hitPlayer(int playerID) {
        dm.playerTable.get(playerID).addCard(cards.getCardList().get(0));
        cards.getCardList().remove(0);

        if (dm.playerTable.get(playerID).getPossibleHandValues().size() == 0) {
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
        dealer.addCard(cards.getCardList().get(0));
        cards.getCardList().remove(0);

        if (dealer.getHand().size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
