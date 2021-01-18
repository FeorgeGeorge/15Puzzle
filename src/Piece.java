public class Piece {
    private final int value;
    // integer > 0 for a number. -1 for undefined (empty)
    public Piece(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isBlank() {
        return (value == -1);
    }
}
