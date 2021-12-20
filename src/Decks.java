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
    private int minShuffles = 5;
    private int maxShuffles = 100;

    /**
     * 
     * 
     * @param numberOfDecks
     */
    Decks(int numberOfDecks) {
        cardList = new ArrayList<>();
        
        for (int i = 0; i < numberOfDecks; i++) {
            createDeck();
        }

        int shuffleTime = rand.nextInt(((maxShuffles - minShuffles) + 1) + minShuffles);
        for (int i = 0; i < shuffleTime; i++) {
            Collections.shuffle(cardList);
        }
    }

    /**
     * 
     * 
     * @return
     */
    public List<Card> getCardList() {
        return cardList;
    }

    /**
     * 
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
}
