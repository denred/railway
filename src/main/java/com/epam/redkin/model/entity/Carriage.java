package com.epam.redkin.model.entity;

import java.util.Objects;

public class Carriage {
    private int carriageId;
    private CarriageType type;
    private String number;
    private int trainId;

    public Carriage() {
    }

    public Carriage(int carriageId, CarriageType type, String number, int trainId) {
        this.carriageId = carriageId;
        this.type = type;
        this.number = number;
        this.trainId = trainId;
    }

    public int getCarriageId() {
        return carriageId;
    }

    public void setCarriageId(int carriageId) {
        this.carriageId = carriageId;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public CarriageType getType() {
        return type;
    }

    public void setType(CarriageType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carriage)) return false;
        Carriage carriage = (Carriage) o;
        return getCarriageId() == carriage.getCarriageId() && getTrainId() == carriage.getTrainId() && getType() == carriage.getType() && getNumber().equals(carriage.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCarriageId(), getType(), getNumber(), getTrainId());
    }
}
