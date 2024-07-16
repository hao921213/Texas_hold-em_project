package program;

public class Poker {
    // Enum for card suits
    public enum Suit {
        HEARTS("Hearts"), SPADES("Spades"), DIAMONDS("Diamonds"), CLUBS("Clubs");

        private final String name;

        Suit(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // Enum for card ranks
    public enum Number {
        ACE("Ace", 14), TWO("2", 2), THREE("3", 3), FOUR("4", 4),
        FIVE("5", 5), SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8),
        NINE("9", 9), TEN("10", 10), JACK("Jack", 11),
        QUEEN("Queen", 12), KING("King", 13);

        private final String rank;
        private final int value;

        Number(String rank, int value) {
            this.rank = rank;
            this.value = value;
        }

        public String getRank() {
            return rank;
        }

        public int getValue() {
            return value;
        }
    }
}
