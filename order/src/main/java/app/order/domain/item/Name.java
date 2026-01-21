package app.order.domain.item;

public record Name(String value) {
    public Name {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (value.length() > 200) {
            throw new IllegalArgumentException("Name cannot exceed 200 characters");
        }
    }
}
