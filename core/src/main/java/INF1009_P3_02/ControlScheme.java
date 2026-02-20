package INF1009_P3_02;

public enum ControlScheme {
    WASD("WASD"),
    ARROW_KEYS("Arrow Keys");

    private final String displayName;

    ControlScheme(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
