/**
 * 
 */
public class Card {
    public static enum Suit {CLUB, SPADE, HEART, DIAMOND}
    public static enum Value {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}
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
        String output = null;

        switch (suit) {
            case CLUB:
                output = "Club";
                break;
            case SPADE:
                output = "Spade";
                break;
            case HEART:
                output = "Heart";
                break;
            case DIAMOND:
                output = "Diamond";
                break;
        }

        return output;
    }

    /**
     * 
     * 
     * @return
     */
    public String getValueString() {
        String output = null;

        switch (value) {
            case ACE:
                output = "Ace";
                break;
            case TWO:
                output = "2";
                break;
            case THREE:
                output = "3";
                break;
            case FOUR:
                output = "4";
                break;
            case FIVE:
                output = "5";
                break;
            case SIX:
                output = "6";
                break;
            case SEVEN:
                output = "7";
                break;
            case EIGHT:
                output = "8";
                break;
            case NINE:
                output = "9";
                break;
            case TEN:
                output = "10";
                break;
            case JACK:
                output = "Jack";
                break;
            case QUEEN:
                output = "Queen";
                break;
            case KING:
                output = "King";
                break;
        }

        return output;
    }
}
