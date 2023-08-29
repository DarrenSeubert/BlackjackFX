/**
 * Class that represents a player in Blackjack
 * 
 * @author Darren Seubert
 */
public class Player {
    private int ID;
    private String name;
    private double cash;
    private int[] wagers;
    private Hand[] hands;

    /**
     * Constructor for a Player
     * 
     * @param ID The ID number of the player
     * @param name The name of the player
     * @param cash The amount of cash the player has
     */
    public Player(int ID, String name, double cash) {
        this.ID = ID;
        this.name = name;
        this.cash = cash;
        wagers = new int[4];
        hands = new Hand[8];
    }

    /**
     * Getter method for the player's ID number
     * 
     * @return ID number of the player
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter method for the player's name
     * 
     * @return Name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the player's cash
     * 
     * @return Amount of cash the player has
     */
    public double getCash() {
        return cash;
    }

    /**
     * Getter method for a specific hand's wager. Hands are indexed as follows (left to right): <p>
     * 
     * @param playerIndex (0-3)
     * @return
     */
    public int getWager(int playerIndex) {
        return wagers[playerIndex];
    }

    /**
     * Getter method for the wagers array (length of 4)
     * 
     * @return The wagers array
     */
    public int[] getWagers() {
        return wagers;
    }

    /**
     * Getter method for a specific hand. Hands are indexed as follows (left to right): <p>
     * 
     * Seat 1) Main Hand: Index 0
     *         Split Hand: Index 1
     * Seat 2) Main Hand: Index 2
     *         Split Hand: Index 3
     * Seat 3) Main Hand: Index 4
     *         Split Hand: Index 5
     * Seat 4) Main Hand: Index 6
     *         Split Hand: Index 7
     * 
     * @param handIndex The index of the hand, 0-8
     * @return The hand at the given index
     */
    public Hand getHand(int playerIndex, boolean isSplitHand) {
        int index = playerIndex * 2;
        if (isSplitHand) {
            index += 1;
        }
        return hands[index];
    }

    /**
     * Getter method for the hands array (length of 8)
     * 
     * @return The hands array
     */
    public Hand[] getHands() {
        return hands;
    }

    /**
     * Method for if a given seatIndex has a split hand
     * 
     * @param playerIndex The number of the given seat (0-3)
     * @return If the given seat number has a split hand or not
     */
    public boolean hasSplitHand(int playerIndex) {
        int index = playerIndex * 2 + 1;
        return hands[index] != null;
    }

    /**
     * 
     * 
     * @param amount
     * @param dm
     */
    public void setCash(double amount, DataManager dm) {
        cash = dm.playerTable.get(ID).getCash() + amount;
        dm.playerTable.put(ID, this);
        dm.updateExistingPlayerInPlayersFile(ID);
    }

    /**
     * 
     * 
     * @param playerIndex (0-3)
     * @param wager 
     */
    public void setWager(int playerIndex, int wager) {
        wagers[playerIndex] = wager;
    }

    /**
     * Method that adds a card to a players hand at the given index. Hands are indexed as follows (left to right): <p>
     * 
     * Seat 1) Main Hand: Index 0
     *         Split Hand: Index 1
     * Seat 2) Main Hand: Index 2
     *         Split Hand: Index 3
     * Seat 3) Main Hand: Index 4
     *         Split Hand: Index 5
     * Seat 4) Main Hand: Index 6
     *         Split Hand: Index 7
     * @param playerIndex
     * @param isSplitHand
     * @param card The card to add to the hand
     */
    public void addCard(int playerIndex, boolean isSplitHand, Card card) {
        int index = playerIndex * 2;
        if (isSplitHand) {
            index += 1;
        }

        if (hands[index] == null) {
            hands[index] = new Hand();    
        }

        hands[index].addCard(card);
    }

    /**
     * Method that clears the player's wagers array
     */
    public void clearWagers() {
        for (int i = 0; i < wagers.length; i++) {
            wagers[i] = 0;
        }
    }

    /**
     * Method that clears the player's hands array
     */
    public void clearHands() {
        for (int i = 0; i < hands.length; i++) {
            hands[i] = null;
        }
    }
}
