enum Suit {Club, Spade, Diamond, Heart}
enum Value {Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King}

/**
 * 
 */
public class Card {
    private Suit suit;
    private Value value;

    /**
     * 
     * 
     * @param suit
     * @param value
     */
    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    /**
     * 
     * 
     * @return
     */
    public Value getValue() {
        return value;
    }

    /**
     * 
     * 
     * @return
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * 
     * 
     * @return
     */
    public String getSuitString() {
        return suit.toString();
    }

    /**
     * 
     * 
     * @return
     */
    public String getValueString() {
        return value.toString();
    }
}
