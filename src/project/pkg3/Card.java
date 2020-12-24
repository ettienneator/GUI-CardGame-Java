package project.pkg3;

/**
 *
 * @author Ettienne Martinez
 */
public class Card {
    
    String cardSuit;
    String cardValue;

    public Card(String cardValue, String cardSuite) {
        this.cardSuit = cardSuite;
        this.cardValue = cardValue;
    }
    
    public String getCardValue() {
        return cardValue;
    }

    public void setCardValue(String cardValue) {
        this.cardValue = cardValue;
    }

    public String getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(String cardSuit) {
        this.cardSuit = cardSuit;
    }

    @Override
    public String toString() {
        return (cardValue + " of " + cardSuit);
    }
}