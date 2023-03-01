package com.epam.redkin.railway.model.dto;

import com.epam.redkin.railway.model.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class BookingDTO {
    private String bookingId;
    private LocalDateTime bookingDate;
    private LocalDateTime dispatchDate;
    private LocalDateTime arrivalDate;
    private String travelTime;
    private double price;
    private OrderStatus bookingStatus;
    private int userId;
    private int routeId;
    private int trainId;
    private int dispatchStationId;
    private int arrivalStationId;
    private int carriageId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDTO that = (BookingDTO) o;
        return Double.compare(that.price, price) == 0 &&
                userId == that.userId &&
                routeId == that.routeId &&
                trainId == that.trainId &&
                dispatchStationId == that.dispatchStationId &&
                arrivalStationId == that.arrivalStationId &&
                carriageId == that.carriageId &&
                Objects.equals(bookingId, that.bookingId) &&
                Objects.equals(bookingDate, that.bookingDate) &&
                Objects.equals(dispatchDate, that.dispatchDate) &&
                Objects.equals(arrivalDate, that.arrivalDate) &&
                Objects.equals(travelTime, that.travelTime) &&
                bookingStatus == that.bookingStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, bookingDate, dispatchDate, arrivalDate, travelTime, price, bookingStatus, userId, routeId, trainId, dispatchStationId, arrivalStationId, carriageId);
    }

    private BookingDTO() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BookingDTO instance = new BookingDTO();

        private Builder() {
        }

        public Builder withBookingId(String bookingId) {
            instance.bookingId = bookingId;
            return this;
        }

        public Builder withBookingDate(LocalDateTime bookingDate) {
            instance.bookingDate = bookingDate;
            return this;
        }

        public Builder withDispatchDate(LocalDateTime dispatchDate) {
            instance.dispatchDate = dispatchDate;
            return this;
        }

        public Builder withArrivalDate(LocalDateTime arrivalDate) {
            instance.arrivalDate = arrivalDate;
            return this;
        }

        public Builder withTravelTime(String travelTime) {
            instance.travelTime = travelTime;
            return this;
        }

        public Builder withPrice(double price) {
            instance.price = price;
            return this;
        }

        public Builder withBookingStatus(OrderStatus bookingStatus) {
            instance.bookingStatus = bookingStatus;
            return this;
        }

        public Builder withUserId(int userId) {
            instance.userId = userId;
            return this;
        }

        public Builder withRouteId(int routeId) {
            instance.routeId = routeId;
            return this;
        }

        public Builder withTrainId(int trainId) {
            instance.trainId = trainId;
            return this;
        }

        public Builder withDispatchStationId(int dispatchStationId) {
            instance.dispatchStationId = dispatchStationId;
            return this;
        }

        public Builder withArrivalStationId(int arrivalStationId) {
            instance.arrivalStationId = arrivalStationId;
            return this;
        }

        public Builder withCarriageId(int carriageId) {
            instance.carriageId = carriageId;
            return this;
        }

        public BookingDTO build() {
            return instance;
        }
    }

    public String getBookingId() {
        return bookingId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public LocalDateTime getDispatchDate() {
        return dispatchDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public double getPrice() {
        return price;
    }

    public OrderStatus getBookingStatus() {
        return bookingStatus;
    }

    public int getUserId() {
        return userId;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getTrainId() {
        return trainId;
    }

    public int getDispatchStationId() {
        return dispatchStationId;
    }

    public int getArrivalStationId() {
        return arrivalStationId;
    }

    public int getCarriageId() {
        return carriageId;
    }
}

