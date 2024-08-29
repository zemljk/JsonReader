package org.example.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightService {

    private final List<Flight> flights;

    public FlightService(List<Flight> flights) {
        this.flights = flights;
    }

    public Map<String, Duration> getMinFlightTimePerCarrier() {
        return flights.stream()
                .filter(flight -> flight.getOrigin().equals("VVO") && flight.getDestination().equals("TLV"))
                .collect(Collectors.groupingBy(
                        Flight::getCarrier,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .map(flight -> calculateDuration(flight.getDepartureDateTime(), flight.getArrivalDateTime()))
                                        .min(Duration::compareTo)
                                        .orElse(Duration.ZERO)
                        )
                ));
    }

    public static Duration calculateDuration(LocalDateTime departure, LocalDateTime arrival) {
        if (departure == null || arrival == null) {
            throw new IllegalArgumentException("Departure and arrival times cannot be null");
        }
        // Calculate duration between departure and arrival
        return Duration.between(departure, arrival);
    }

    public double getPriceDifference() {
        List<Integer> prices = flights.stream()
                .filter(flight -> flight.getOrigin().equals("VVO") && flight.getDestination().equals("TLV"))
                .map(Flight::getPrice)
                .sorted()
                .collect(Collectors.toList());

        if (prices.isEmpty()) {
            return 0.0;
        }

        double median = calculateMedian(prices);
        double mean = prices.stream().mapToInt(Integer::intValue).average().orElse(0);

        return mean - median;
    }

    private double calculateMedian(List<Integer> prices) {
        int size = prices.size();
        if (size % 2 == 0) {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }
}
