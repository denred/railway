package com.epam.redkin.railway.util.uuid;

import java.util.UUID;

public class ReservationIDGenerator {
    public static String generateReservationID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").substring(0, 10);
    }
}
