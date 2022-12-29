package com.epam.redkin.model.entity;

public enum CarriageType {
    FIRST_CLASS(54.0),
    SECOND_CLASS(44.0);

    private double price;

    CarriageType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
