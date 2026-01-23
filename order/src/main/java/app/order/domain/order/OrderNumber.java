package app.order.domain.order;

public record OrderNumber(String value) {
    public OrderNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("OrderNumber cannot be null or blank");
        }
        if (value.length() > 50) {
            throw new IllegalArgumentException("OrderNumber cannot exceed 50 characters");
        }
        if (!value.matches("^ORD-[0-9]+$")) {
            throw new IllegalArgumentException("OrderNumber must follow pattern ORD-XXXXXXXXX, e.g. ORD-123456");
        }
    }
}
