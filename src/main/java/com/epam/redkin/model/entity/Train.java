package com.epam.redkin.model.entity;

public class Train {
    private int id;
    private String number;

    public Train() {
    }

    public Train(String number) {
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
}
