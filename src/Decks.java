import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Card.Suit;
import Card.Value;

public class Decks {
    List<Card> cardList = new ArrayList<>();

    Decks(int numberOfDecks) {
        for (int i = 0; i < numberOfDecks; i++) {
            createDeck();
        }
    }

    private void createDeck() {
        cardList.add(new Card(Suit.CLUB, Value.ACE));
        Collections.shuffle(cardList);
    }
}
