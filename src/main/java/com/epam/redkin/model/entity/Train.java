package com.epam.redkin.model.entity;

import java.util.Objects;

public class Train {
    private int id;
    private String number;

    public Train() {
    }

    public Train(String number) {
        this.number = number;
    }

    public Train(int id, String number) {
        this.id = id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train)) return false;
        Train train = (Train) o;
        return getNumber().equals(train.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }
}
