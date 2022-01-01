import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a Player in Blackjack
 * 
 * @author Darren Seubert
 */
public class Player {
    private String name;
    private int IDNumber;
    private int cash;
    private List<Card> hand;

    /**
     * 
     * 
     * @param IDNumber
     * @param name
     * @param cash
     */
    public Player(int IDNumber, String name, int cash) {
        this.name = name;
        this.IDNumber = IDNumber;
        this.cash = cash;
        hand = new ArrayList<>();
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
    public int getCash() {
        return cash;
    }

    /**
     * Getter method for the player's hand of cards
     * 
     * @return 
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * 
     * 
     * @param amount
     */
    public void setCash(int amount) {
        cash += amount;
    }

    /**
     * 
     * 
     * @param card
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * 
     * 
     * @return
     */
    public List<Integer> getPossibleHandValues() {
        List<Integer> possibleHandValues = new ArrayList<>();
        int numberOfAces = 0;
        int handValue = 0;

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == Value.Ace) {
                numberOfAces++;
            }

            handValue += hand.get(i).getValueInt();
        }

        if (handValue <= 21) {
            possibleHandValues.add(handValue);
        }

        for (int i = 0; i < numberOfAces; i++) {
            handValue += 10;

            if (handValue <= 21) {
                possibleHandValues.add(handValue);
            }
        }

        return possibleHandValues;
    }

    /**
     * 
     */
    public void clearHand() {
        hand.removeAll(hand);
    }
}
