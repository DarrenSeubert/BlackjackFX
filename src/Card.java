/**
 * Class that represents a playing card
 * 
 * @author Darren Seubert
 */
public class Card {
    public enum Value {Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Cut}
    public enum Suit {Club, Spade, Diamond, Heart, Cut}
    private Suit suit;
    private Value value;
    private boolean isFaceUp;
    
    /**
     * 
     * 
     * @param suit
     * @param value
     */
    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
        isFaceUp = false;
    }


    /**
     * Getter method for a card's value
     * 
     * @return Value of the card
     */
    public Value getValue() {
        return value;
    }

    /**
     * Getter method for a card's suit
     * 
     * @return Suit of the card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Getter method for if the card is face up
     * 
     * @return True if card is face up, else false
     */
    public boolean getIsFaceUp() {
        return isFaceUp;
    }

    /**
     * Method that returns the value of the card represented as an integer (Cut card represented as 
     * a 0)
     * 
     * @return Value of card represented as a integer
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
            case Cut:
                valueInt = 0;
                break;
        }

        return valueInt;
    }

    /**
     * Setter method to set card's isFaceUp boolean
     * 
     * @param isFaceUp value that isFaceUp should be set to
     */
    public void setIsFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
