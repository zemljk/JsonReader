package org.example.entities;

import java.time.LocalDateTime;

public class Flight {
    private String origin;
    private String destination;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String carrier;
    private int price;

    public Flight(String origin, String destination, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, String carrier, int price) {
        this.origin = origin;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.carrier = carrier;
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public String getCarrier() {
        return carrier;
    }

    public int getPrice() {
        return price;
    }
}
