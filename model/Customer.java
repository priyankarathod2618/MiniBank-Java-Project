
package model;

public class Customer implements Cloneable {

    private static int counter = 100;

    private final String customerId;
    private String name;
    private String email;
    private String mobile;
    private Address address;

    public Customer(String name, String email, String mobile) {
        this.customerId = "CUST" + (++counter);
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public Customer(String name, String email, String mobile, Address address) {
        this(name, email, mobile);
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public Address getAddress() {
        return address;
    }

    // Static nested class
    public static class Address {
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() {
            return line;
        }

        public String getCity() {
            return city;
        }

        public String getPincode() {
            return pincode;
        }

        @Override
        public String toString() {
            return line + ", " + city + " - " + pincode;
        }
    }

    // Creates a copy of the customer
    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId
                + ", Name: " + name
                + ", Email: " + email
                + ", Mobile: " + mobile
                + ", Address: "
                + (address == null ? "Not provided" : address);
    }
}
