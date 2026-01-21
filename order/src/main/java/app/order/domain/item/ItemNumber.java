package app.order.domain.item;

public record ItemNumber(String value) {
    public ItemNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ItemNumber cannot be null or blank");
        }
        if (value.length() > 50) {
            throw new IllegalArgumentException("ItemNumber cannot exceed 50 characters");
        }
        if (!value.matches("^[a-zA-Z0-9]+-[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("ItemNumber must contain alphanumeric characters with a hyphen, e.g. 123-A");
        }
    }
}
