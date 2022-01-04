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
     */
    public Decks getDecks() {
        return decks;
    }

    public DataManager getDm() {
        return dm;
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
     * @param addAmount
     * @return
     */
    public boolean addCashToPlayer(int playerID, int addAmount) {
        if (dm.playerTable.get(playerID) == null || addAmount <= 0) {
            return false;
        } else {
            Player updatedPlayer = new Player(playerID, dm.playerTable.get(playerID).getName(), dm.playerTable.get(playerID).getCash() + addAmount);
            dm.playerTable.put(playerID, updatedPlayer);
            dm.updateExistingPlayerInPlayerFile(playerID, dm.playerTable.get(playerID).getCash());
            return true;
        }
    }

    /**
     * 
     * 
     * @param playerID
     * @param subtractAmount Value must be postive number
     * @return
     */
    public boolean subtractCashFromPlayer(int playerID, int subtractAmount) {
        if (dm.playerTable.get(playerID) == null || subtractAmount <= 0) {
            return false;
        } else if (dm.playerTable.get(playerID).getCash() - subtractAmount < 0) {
            Player updatedPlayer = new Player(playerID, dm.playerTable.get(playerID).getName(), 0);
            dm.playerTable.put(playerID, updatedPlayer);
            dm.updateExistingPlayerInPlayerFile(playerID, dm.playerTable.get(playerID).getCash());
            return true;
        } else {
            Player updatedPlayer = new Player(playerID, dm.playerTable.get(playerID).getName(), dm.playerTable.get(playerID).getCash() - subtractAmount);
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
    public boolean hitPlayer(int playerID) {
        dm.playerTable.get(playerID).addCard(decks.getCardList().get(0));
        decks.getCardList().remove(0);

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
        dealer.addCard(decks.getCardList().get(0));
        decks.getCardList().remove(0);

        if (dealer.getHand().size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
