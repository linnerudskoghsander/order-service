package app.order.helper;

import app.order.domain.customer.ContactInfo;
import app.order.domain.customer.Customer;
import app.order.domain.customer.Name;
import app.order.domain.customer.ShippingInfo;
import java.util.Optional;
import java.util.Random;

public class CustomerBuilder {
    private static final Random random = new Random();
    private static final String[] FIRST_NAMES = {"John", "Jane", "Michael", "Sarah", "David", "Emma", "Robert", "Lisa"};
    private static final String[] MIDDLE_NAMES = {"Robert", "James", "William", "Maria", "Anne", "Paul", "David"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Davis", "Wilson", "Moore", "Taylor", "Anderson"};
    private static final String[] STREETS = {"Main St", "Oak Ave", "Pine Rd", "Elm Street", "Maple Drive", "Cedar Lane", "Birch Road"};
    private static final String[] CITIES = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Seattle", "Boston"};

    public static Customer Customer() {
        Name name = generateRandomName();
        ContactInfo contactInfo = generateRandomContactInfo();
        ShippingInfo shippingInfo = generateRandomShippingInfo();

        return new Customer(name, contactInfo, shippingInfo);
    }

    private static Name generateRandomName() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        Optional<String> middleName = random.nextBoolean()
            ? Optional.of(MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)])
            : Optional.empty();
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

        return new Name(firstName, middleName, lastName);
    }

    private static ContactInfo generateRandomContactInfo() {
        String phoneNumber = generateRandomPhoneNumber();
        String email = generateRandomEmail();

        return new ContactInfo(
            new ContactInfo.PhoneNumber(phoneNumber),
            new ContactInfo.Email(email)
        );
    }

    private static String generateRandomPhoneNumber() {
        return String.format("+47 %08d", random.nextInt(100000000));
    }

    private static String generateRandomEmail() {
        String[] domains = {"example.com", "test.com", "mail.com", "value.com"};
        String domain = domains[random.nextInt(domains.length)];
        String username = "user" + random.nextInt(100000);
        return username + "@" + domain;
    }

    private static ShippingInfo generateRandomShippingInfo() {
        String address = generateRandomAddress();
        int streetNumber = 1 + random.nextInt(999);
        int postalCode = 1000 + random.nextInt(8999);

        return new ShippingInfo(
            new ShippingInfo.Address(address),
            new ShippingInfo.StreetNumber(streetNumber),
            new ShippingInfo.PostalCode(postalCode)
        );
    }

    private static String generateRandomAddress() {
        String street = STREETS[random.nextInt(STREETS.length)];
        String city = CITIES[random.nextInt(CITIES.length)];
        return city + ", " + street;
    }
}

