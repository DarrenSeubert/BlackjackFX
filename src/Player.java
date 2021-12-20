import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Player {
    private String name;
    private int cash;
    private List<Card> hand;

    /**
     * 
     * @param name
     */
    public Player(String name) {
        this.name = name;
        this.cash = 100;
        hand = new ArrayList<>();
    }

    public Player(Player playerToExtend) {
        this.name = playerToExtend.getName();
        this.cash = -1;
        hand = new ArrayList<>();
    }

    /**
     * 
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * 
     * @return
     */
    public int getCash() {
        return cash;
    }

    /**
     * 
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
