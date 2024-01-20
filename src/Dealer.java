/**
 * Class that represents a dealer in Blackjack
 * 
 * @author Darren Seubert
 */
public class Dealer {
    private Hand hand;

    /**
     * 
     */
    public Dealer() {
        hand = new Hand();
    }

    /**
     * 
     * 
     * @return
     */
    public Hand getHand() {
        return hand;
    }
}
