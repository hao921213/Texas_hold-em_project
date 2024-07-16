package program;

public class GameLogic {
    private Deck deck;
    private Player[] players;
    private int currentBet;
    private int currentPlayerIndex = 0;  // Index to track whose turn it is

    public GameLogic() {
        initializeGame();
    }

    // Initialize or reset the game
    private void initializeGame() {
        deck = new Deck();
        deck.shuffle();
        players = new Player[] {
            new Player("Alice", 1000),
            new Player("Bob", 1000),
            new Player("Charlie", 1000),
            new Player("Dana", 1000)
        };
        currentBet = 0;
        currentPlayerIndex = 0;  // Start from the first player
        for (Player player : players) {
            player.resetPlayer();
        }
    }

    // Reset player states for a new game
    private void resetPlayerStates() {
        for (Player player : players) {
            player.resetPlayer();
        }
    }

    // Public method to reset the game
    public void resetGame() {
        initializeGame();
    }

    // Game actions
    public void fold() {
        players[currentPlayerIndex].fold();
        moveToNextPlayer();
    }

    public void check() {
        if (currentBet == 0) {
            System.out.println("Player checks");
            moveToNextPlayer();
        } else {
            System.out.println("Check not allowed, current bet is: " + currentBet);
        }
    }

    public void call() {
        int amountToCall = currentBet - players[currentPlayerIndex].getCurrentBet();
        players[currentPlayerIndex].updateBet(amountToCall);
        System.out.println("Player calls with " + amountToCall);
        moveToNextPlayer();
    }

    // Modified raise method to accept an integer parameter for the raise amount
    public void raise(int raiseAmount) {
        int newTotalBet = players[currentPlayerIndex].getTotalContribution() + raiseAmount;
        if (newTotalBet > currentBet) {
            currentBet = newTotalBet; // Update the highest current bet
        }
        players[currentPlayerIndex].updateBet(raiseAmount);

        // Apply changes to all previous players to match the new current bet
        for (int i = 0; i < currentPlayerIndex; i++) {
            if (players[i].isInGame()) {
                players[i].adjustForNewTotalBet(currentBet);
            }
        }
        
        System.out.println("Total raised amount is now: " + currentBet);
        moveToNextPlayer();
    }

    public void dealCards() {
        for (Player player : players) {
            if (player.isInGame()) {
                player.receiveCard(deck.dealCard());
                player.receiveCard(deck.dealCard());
            }
        }
    }
    
    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        if (currentPlayerIndex == 0) { // Reset for a new betting round
            currentBet = 0;
        }
    }

    public Player[] getPlayers() {
        return players;
    }
    
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
}