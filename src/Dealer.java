import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a dealer in Blackjack
 * 
 * @author Darren Seubert
 */
public class Dealer {
    private List<Card> hand;

    /**
     * 
     */
    public Dealer() {
        hand = new ArrayList<>();
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
     * @param
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
            if (hand.get(i).getValue() == Card.Value.Ace) {
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
