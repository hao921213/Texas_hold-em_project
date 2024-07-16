package program;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;
    private boolean isInGame;
    private int currentBet;  // Track the current bet amount
    private IntegerProperty chips;  // Use IntegerProperty for chips
    private int totalContribution;  // Total contributed to the pot this round

    public Player(String name, int initialChips) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.isInGame = true;
        this.currentBet = 0;
        this.chips = new SimpleIntegerProperty(initialChips);
        this.totalContribution = 0;
    }

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public void fold() {
        isInGame = false;
        hand.clear();
    }

    public boolean isInGame() {
        return isInGame;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void updateBet(int amount) {
        totalContribution += amount;
        currentBet = totalContribution;
        adjustChips();
    }

    public void adjustChips() {
        chips.set(chips.get() - currentBet);
    }

    public void clearBet() {
        currentBet = 0;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public String getName() {
        return name;
    }

    public final IntegerProperty chipsProperty() {
        return this.chips;
    }

    public final int getChips() {
        return chips.get();
    }

    public final void setChips(int value) {
        chips.set(value);
    }

    public int getTotalContribution() {
        return totalContribution;
    }

    // Ensure this method correctly manages adjustments for new total bets
    public void adjustForNewTotalBet(int newTotalBet) {
        int needed = newTotalBet - totalContribution;
        if (needed > 0) {
            updateBet(needed);
        }
    }

    public void resetPlayer() {
        hand.clear();
        isInGame = true;
        currentBet = 0;
        totalContribution = 0;
        chips.set(1000);  // Reset to initial chips
    }
    
    
}
