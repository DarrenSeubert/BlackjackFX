import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a player in Blackjack
 * 
 * @author Darren Seubert
 */
public class Player {
    private int IDNumber;
    private String name;
    private double cash;
    private List<Hand> handList;

    /**
     * 
     * 
     * @param IDNumber
     * @param name
     * @param cash
     */
    public Player(int IDNumber, String name, double cash) {
        this.IDNumber = IDNumber;
        this.name = name;
        this.cash = cash;
        handList = new ArrayList<>();
        handList.add(new Hand());
    }

    /**
     * Getter method for the player's ID number
     * 
     * @return ID number of the player
     */
    public int getIDNumber() {
        return IDNumber;
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
     * 
     * 
     * @return
     */
    public List<Hand> getHands() {
        return handList;
    }

    /**
     * Method that creates a new hand for the given player
     * 
     * @return Index of the new hand
     */
    public int addNewHand() {
        handList.add(new Hand());

        return handList.size() - 1;
    }
}
