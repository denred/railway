package com.epam.redkin.service.impl;

import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Order;
import com.epam.redkin.model.entity.OrderStatus;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.repository.OrderRepository;
import com.epam.redkin.model.repository.SeatRepository;
import com.epam.redkin.service.OrderService;
import com.epam.redkin.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;


public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository orderRepository;
    private SeatRepository seatRepository;
    private SeatService seatService;

    public OrderServiceImpl(OrderRepository orderRepository, SeatService seatService, SeatRepository seatRepository) {
        this.orderRepository = orderRepository;
        this.seatRepository = seatRepository;
        this.seatService = seatService;
    }

    @Override
    public void addOrder(Order order, int routsId, List<Seat> seats) {
        order.setPrice(order.getCarrType().getPrice() * order.getCountOfSeats());
        seats.forEach(seat -> seatRepository.reservedSeat(seat.getId()));
        orderRepository.create(order);
    }

    @Override
    public void cancelOrder(int orderId) {
        Order order = getOrderById(orderId);
        LocalDateTime now = LocalDateTime.now();
        order.setOrderStatus(OrderStatus.CANCELED);
        validateDate(order, now);
        String seatNumber = String.valueOf(order.getSeatNumber());

        List<String> seatsNumber = seatService.getSeatsId(seatNumber);
        List<Seat> seatsByIdBatch = seatRepository.getListSeatsByIdBatch(seatsNumber);
        seatsByIdBatch.forEach(seat -> seatRepository.clearSeat(seat.getId()));
        orderRepository.updateOrderStatus(order.getId(), order.getOrderStatus());
    }

    private void validateDate(Order order, LocalDateTime now) {
        if (now.isAfter(order.getArrivalDate()) || now.isEqual(order.getArrivalDate())) {
            IncorrectDataException e = new IncorrectDataException("Can`t cancel the order because the cancellation " +
                    "period has been reached");
            LOGGER.error(e.getMessage());
            throw e;
        }
    }


    @Override
    public List<Order> getOrderByUserId(int userId) {
        return orderRepository.getOrderByUserId(userId);
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        if (status == OrderStatus.DECLINED || status == OrderStatus.CANCELED) {
            Order order = orderRepository.read(orderId);
            String seatNumber = String.valueOf(order.getSeatNumber());
            List<String> seatsId = seatService.getSeatsId(seatNumber);
            List<Seat> seatsByIdBatch = seatRepository.getListSeatsByIdBatch(seatsId);
            seatsByIdBatch.forEach(seat -> seatRepository.clearSeat(seat.getId()));
        }
        return orderRepository.updateOrderStatus(orderId, status);
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.read(orderId);
    }

    @Override
    public List<Order> getAllOrderList() {
        return orderRepository.getAllOrders();
    }

    @Override
    public Double getPrice(String carType, int countOfSeats) {
        return CarriageType.valueOf(carType).getPrice() * countOfSeats;
    }

    @Override
    public Double getPriceOfSuccessfulOrders(int userId) {
        return orderRepository.getPriceOfSuccessfulOrders(userId);
    }

    @Override
    public List<Order> getOrderListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage) {
        List<Order> allRecords = orderRepository.getAllOrders();
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getOrderListSize() {
        return orderRepository.getAllOrders().size();
    }

    @Override
    public List<Order> getOrderListByUserIdAndByCurrentRecordAndRecordsPerPage(String userId, int currentPage, int recordsPerPage) {
        List<Order> allRecords = orderRepository.getOrderByUserId(Integer.parseInt(userId));
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getOrderListSizeByUserId(String userId) {
        return orderRepository.getOrderByUserId(Integer.parseInt(userId)).size();
    }


}
