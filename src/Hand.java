import java.util.ArrayList;

/**
 * Class that represents a hand of cards in Blackjack
 * 
 * @author Darren Seubert
 */
public class Hand {
    private ArrayList<Card> cardList;

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
    public ArrayList<Card> getCardList() {
        return cardList;
    }

    /**
     * 
     * 
     * @param card
     */
    public void addCard(Card card) {
        cardList.add(card);
    }

    /**
     * 
     * 
     * @return
     */
    public ArrayList<Integer> getPossibleHandValues() {
        ArrayList<Integer> possibleHandValues = new ArrayList<>();
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

    @Override
    public String toString() {
        return cardList.toString();
    }
}
