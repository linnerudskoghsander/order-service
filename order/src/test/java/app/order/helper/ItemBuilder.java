package app.order.helper;

import app.order.domain.item.Description;
import app.order.domain.item.Item;
import app.order.domain.item.ItemNumber;
import app.order.domain.item.Name;
import java.math.BigDecimal;
import java.util.Random;

public class ItemBuilder {
    private static final Random random = new Random();
    private static final String[] ITEM_PREFIXES = {"PROD", "ITEM", "SKU", "ART"};
    private static final String[] ITEM_NAMES = {"Widget", "Gadget", "Tool", "Device", "Component", "Module", "Unit"};
    private static final String[] ITEM_DESCRIPTIONS = {
            "High quality product",
            "Professional grade",
            "Industrial standard",
            "Premium selection",
            "Durable and reliable",
            "Easy to use",
            "Highly efficient"
    };

    public static Item Item() {
        String itemNumber = generateRandomItemNumber();
        String name = generateRandomName();
        String description = generateRandomDescription();
        BigDecimal price = generateRandomPrice();
        int quantity = generateRandomQuantity();

        return new Item(
                new ItemNumber(itemNumber),
                new Name(name),
                new Description(description),
                price,
                quantity
        );
    }

    private static String generateRandomItemNumber() {
        String prefix = ITEM_PREFIXES[random.nextInt(ITEM_PREFIXES.length)];
        int number = 100 + random.nextInt(9900);
        return prefix + "-" + number;
    }

    private static String generateRandomName() {
        String baseName = ITEM_NAMES[random.nextInt(ITEM_NAMES.length)];
        String variant = "v" + (random.nextInt(9) + 1);
        return baseName + " " + variant;
    }

    private static String generateRandomDescription() {
        return ITEM_DESCRIPTIONS[random.nextInt(ITEM_DESCRIPTIONS.length)];
    }

    private static BigDecimal generateRandomPrice() {
        double price = 10 + random.nextDouble() * 490;
        return BigDecimal.valueOf(price).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private static int generateRandomQuantity() {
        return random.nextInt(1000);
    }
}
