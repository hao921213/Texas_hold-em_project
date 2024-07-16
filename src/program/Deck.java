package program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        for (Poker.Suit suit : Poker.Suit.values()) {
            for (Poker.Number number : Poker.Number.values()) {
                cards.add(new Card(suit, number));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);  // Remove the top card from the deck and return it
        } else {
            throw new IllegalStateException("No cards left in the deck");
        }
    }

    public int remainingCards() {
        return cards.size();
    }
}
