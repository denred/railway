package com.epam.redkin.model.entity;

public class Seat {
    private int id;
    private int carriageId;
    private String seatNumber;
    private boolean busy;

    public Seat() {
    }

    public Seat(int id, int carriageId, String number, boolean busy) {
        this.id = id;
        this.carriageId = carriageId;
        this.seatNumber = number;
        this.busy = busy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarriageId() {
        return carriageId;
    }

    public void setCarriageId(int carriageId) {
        this.carriageId = carriageId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
