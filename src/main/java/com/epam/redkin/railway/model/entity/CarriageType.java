package com.epam.redkin.railway.model.entity;

public enum CarriageType {
    FIRST_CLASS(872.58),
    SECOND_CLASS(487.22),
    COMPARTMENT(983.79),
    BERTH(274.84),
    DE_LUXE(1567.69);

    private double price;

    CarriageType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
