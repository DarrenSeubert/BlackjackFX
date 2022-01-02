import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class that represents the decks of cards
 * 
 * @author Darren Seubert
 */
public class Decks {
    private Random rand = new Random();
    private List<Card> cardList;
    private List<Card> usedCardList;
    private int numberOfShuffles;
    private int cutCardIndex;
    private final int MIN_SHUFFLES = 5;
    private final int MAX_SHUFFLES = 100;
    private final int CUT_MIN_OFFSET = 60;
    private final int CUT_MAX_OFFSET = 75;

    /**
     * Constructor for the Decks of cards
     * 
     * @param numberOfDecks
     */
    public Decks(int numberOfDecks) { // numOfDecks hardcoded to 6 for now
        cardList = new ArrayList<>();
        usedCardList = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            createDeck();
        }

        numberOfShuffles = rand.nextInt((MAX_SHUFFLES - MIN_SHUFFLES) + 1) + MIN_SHUFFLES;
        
        for (int i = 0; i < numberOfShuffles; i++) {
            Collections.shuffle(cardList);
        }

        if (numberOfDecks >= 6) {
            cutCardIndex = cardList.size() - (rand.nextInt((CUT_MAX_OFFSET - CUT_MIN_OFFSET) + 1) +
                CUT_MIN_OFFSET);
            
            cardList.add(cutCardIndex, new Card(Card.Suit.Cut, Card.Value.Cut));
        }
    }

    /**
     * Getter method for Card List
     * 
     * @return The list containing the cards
     */
    public List<Card> getCardList() {
        return cardList;
    }

    /**
     * Method that creates a standard deck of 52 cards and add them to the Card List
     */
    private void createDeck() {
        cardList.add(new Card(Card.Suit.Club, Card.Value.Ace));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Ace));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Ace));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Ace));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Two));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Two));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Two));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Two));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Three));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Three));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Three));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Three));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Four));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Four));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Four));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Four));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Five));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Five));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Five));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Five));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Six));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Six));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Six));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Six));
        
        cardList.add(new Card(Card.Suit.Club, Card.Value.Seven));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Seven));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Seven));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Seven));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Eight));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Eight));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Eight));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Eight));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Nine));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Nine));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Nine));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Nine));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Ten));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Ten));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Ten));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Ten));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Jack));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Jack));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Jack));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Jack));

        cardList.add(new Card(Card.Suit.Club, Card.Value.Queen));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.Queen));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.Queen));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.Queen));

        cardList.add(new Card(Card.Suit.Club, Card.Value.King));
        cardList.add(new Card(Card.Suit.Spade, Card.Value.King));
        cardList.add(new Card(Card.Suit.Diamond, Card.Value.King));
        cardList.add(new Card(Card.Suit.Heart, Card.Value.King));
    }

    /**
     * Method that reshuffles the deck of cards
     */
    public void reshuffle() {
        for (Card card : usedCardList) {
            cardList.add(card);
            usedCardList.remove(card);
        }
        usedCardList.clear();

        numberOfShuffles = rand.nextInt(((MAX_SHUFFLES - MIN_SHUFFLES) + 1) + MIN_SHUFFLES);
        for (int i = 0; i < numberOfShuffles; i++) {
            Collections.shuffle(cardList);
        }
    }
}
