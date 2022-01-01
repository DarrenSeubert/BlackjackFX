import java.util.List;

public class BackEnd {
    private Dealer dealer;
    private List<Player> players;
    private Decks cards;

    public BackEnd() {
        dealer = new Dealer();
    }

    public void createDeck(int numberOfDecks) { // TODO Handle in front end int numOfDecks
        cards = new Decks(numberOfDecks);
    }

    public void addPlayerToGame(int playerID) {
        
    }

    public void dealCardToPlayer(String name) {
        if (cards != null && cards.getCardList() != null && cards.getCardList().size() != 0) {
            dealer.addCard(cards.getCardList().get(0));
            cards.getCardList().remove(0);
        }
    }

    public void dealCardToDealer(Dealer dealer) {
        if (cards != null && cards.getCardList() != null && cards.getCardList().size() != 0) {
            dealer.addCard(cards.getCardList().get(0));
            cards.getCardList().remove(0);
        }
    }
}
