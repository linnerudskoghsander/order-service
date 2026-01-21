package app.order.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    private String middleName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 10)
    private String streetNumber;

    @Column(nullable = false, length = 10)
    private String postalCode;

    protected CustomerEntity() {}

    public CustomerEntity(String firstName, String middleName, String lastName, String phoneNumber, String email, String address, String streetNumber, String postalCode) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
    }

    public Long id() { return id; }

    public String firstName() {
        return firstName;
    }

    public String middleName() {
        return middleName;
    }

    public String lastName() {
        return lastName;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String email() {
        return email;
    }

    public String address() {
        return address;
    }

    public String streetNumber() {
        return streetNumber;
    }

    public String postalCode() {
        return postalCode;
    }
}

