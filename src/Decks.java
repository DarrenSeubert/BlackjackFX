import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 
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

        numberOfShuffles = rand.nextInt(((MAX_SHUFFLES - MIN_SHUFFLES) + 1) + MIN_SHUFFLES);
        for (int i = 0; i < numberOfShuffles; i++) {
            Collections.shuffle(cardList);
        }

        if (numberOfDecks >= 6) {
            cutCardIndex = cardList.size() - (rand.nextInt(((CUT_MAX_OFFSET - CUT_MIN_OFFSET) + 1) +
                CUT_MIN_OFFSET));
            
            cardList.add(cutCardIndex, new Card(Suit.Cut, Value.Cut));
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
        cardList.add(new Card(Suit.Club, Value.Ace));
        cardList.add(new Card(Suit.Spade, Value.Ace));
        cardList.add(new Card(Suit.Diamond, Value.Ace));
        cardList.add(new Card(Suit.Heart, Value.Ace));

        cardList.add(new Card(Suit.Club, Value.Two));
        cardList.add(new Card(Suit.Spade, Value.Two));
        cardList.add(new Card(Suit.Diamond, Value.Two));
        cardList.add(new Card(Suit.Heart, Value.Two));

        cardList.add(new Card(Suit.Club, Value.Three));
        cardList.add(new Card(Suit.Spade, Value.Three));
        cardList.add(new Card(Suit.Diamond, Value.Three));
        cardList.add(new Card(Suit.Heart, Value.Three));

        cardList.add(new Card(Suit.Club, Value.Four));
        cardList.add(new Card(Suit.Spade, Value.Four));
        cardList.add(new Card(Suit.Diamond, Value.Four));
        cardList.add(new Card(Suit.Heart, Value.Four));

        cardList.add(new Card(Suit.Club, Value.Five));
        cardList.add(new Card(Suit.Spade, Value.Five));
        cardList.add(new Card(Suit.Diamond, Value.Five));
        cardList.add(new Card(Suit.Heart, Value.Five));

        cardList.add(new Card(Suit.Club, Value.Six));
        cardList.add(new Card(Suit.Spade, Value.Six));
        cardList.add(new Card(Suit.Diamond, Value.Six));
        cardList.add(new Card(Suit.Heart, Value.Six));
        
        cardList.add(new Card(Suit.Club, Value.Seven));
        cardList.add(new Card(Suit.Spade, Value.Seven));
        cardList.add(new Card(Suit.Diamond, Value.Seven));
        cardList.add(new Card(Suit.Heart, Value.Seven));

        cardList.add(new Card(Suit.Club, Value.Eight));
        cardList.add(new Card(Suit.Spade, Value.Eight));
        cardList.add(new Card(Suit.Diamond, Value.Eight));
        cardList.add(new Card(Suit.Heart, Value.Eight));

        cardList.add(new Card(Suit.Club, Value.Nine));
        cardList.add(new Card(Suit.Spade, Value.Nine));
        cardList.add(new Card(Suit.Diamond, Value.Nine));
        cardList.add(new Card(Suit.Heart, Value.Nine));

        cardList.add(new Card(Suit.Club, Value.Ten));
        cardList.add(new Card(Suit.Spade, Value.Ten));
        cardList.add(new Card(Suit.Diamond, Value.Ten));
        cardList.add(new Card(Suit.Heart, Value.Ten));

        cardList.add(new Card(Suit.Club, Value.Jack));
        cardList.add(new Card(Suit.Spade, Value.Jack));
        cardList.add(new Card(Suit.Diamond, Value.Jack));
        cardList.add(new Card(Suit.Heart, Value.Jack));

        cardList.add(new Card(Suit.Club, Value.Queen));
        cardList.add(new Card(Suit.Spade, Value.Queen));
        cardList.add(new Card(Suit.Diamond, Value.Queen));
        cardList.add(new Card(Suit.Heart, Value.Queen));

        cardList.add(new Card(Suit.Club, Value.King));
        cardList.add(new Card(Suit.Spade, Value.King));
        cardList.add(new Card(Suit.Diamond, Value.King));
        cardList.add(new Card(Suit.Heart, Value.King));
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
