import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a hand of cards in Blackjack
 * 
 * @author Darren Seubert
 */
public class Hand {
    private List<Card> cardList;

    /**
     * 
     */
    public Hand() {
        cardList = new ArrayList<>();
    }

    /**
     * Getter method for the player's hand of cards
     * 
     * @return 
     */
    public List<Card> getCardList() {
        return cardList;
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

        for (int i = 0; i < cardList.size(); i++) {
            if (cardList.get(i).getValue() == Card.Value.Ace) {
                numberOfAces++;
            }

            handValue += cardList.get(i).getValueInt();
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
        cardList.removeAll(cardList);
    }

    @Override
    public String toString() {
        return cardList.toString();
    }
}
