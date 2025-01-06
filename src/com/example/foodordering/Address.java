package com.example.foodordering;


public class Address {
    private String street, district, postalCode;

    public Address(String street, String district, String postalCode) {
        this.street = street;
        this.district = district;
        this.postalCode = postalCode;
    }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getdistrict() { return district; }
    public void setdistrict(String district) { this.district = district; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
