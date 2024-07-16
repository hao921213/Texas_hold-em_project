package program;

public class Card {
    private final Poker.Suit suit;
    private final Poker.Number number;

    public Card(Poker.Suit suit, Poker.Number number) {
        this.suit = suit;
        this.number = number;
    }

    public Poker.Suit getSuit() {
        return suit;
    }

    public Poker.Number getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number.getRank() + " of " + suit.getName();
    }
}
