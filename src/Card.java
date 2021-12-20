/**
 * 
 */
enum Suit {
    Club, Spade, Diamond, Heart
}

/**
 * 
 */
enum Value {
    Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
}

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
    public int getValueInt() {
        int valueInt = -1;

        switch (value) {
            case Ace:
                valueInt = 1;
                break;
            case Two:
                valueInt = 2;
                break;
            case Three:
                valueInt = 3;
                break;
            case Four:
                valueInt = 4;
                break;
            case Five:
                valueInt = 5;
                break;
            case Six:
                valueInt = 6;
                break;
            case Seven:
                valueInt = 7;
                break;
            case Eight:
                valueInt = 8;
                break;
            case Nine:
                valueInt = 9;
                break;
            case Ten:
                valueInt = 10;
                break;
            case Jack:
                valueInt = 10;
                break;
            case Queen:
                valueInt = 10;
                break;
            case King:
                valueInt = 10;
                break;
        }

        return valueInt;
    }
}
