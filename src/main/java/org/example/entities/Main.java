package org.example.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("tickets.json")) {
            if (inputStream == null) {
                System.out.println("File tickets.json not found.");
                return;
            }

            JsonNode root = mapper.readTree(inputStream);
            JsonNode ticketNodes = root.get("tickets");

            List<Flight> flights = new ArrayList<>();
            for (JsonNode node : ticketNodes) {
                String origin = node.get("origin").asText();
                String destination = node.get("destination").asText();
                String departureDate = node.get("departure_date").asText();
                String departureTime = node.get("departure_time").asText();
                String arrivalDate = node.get("arrival_date").asText();
                String arrivalTime = node.get("arrival_time").asText();
                String carrier = node.get("carrier").asText();
                int price = node.get("price").asInt();

                LocalDateTime departureDateTime = LocalDateTime.parse(departureDate + " " + departureTime, DATE_TIME_FORMATTER);
                LocalDateTime arrivalDateTime = LocalDateTime.parse(arrivalDate + " " + arrivalTime, DATE_TIME_FORMATTER);

                flights.add(new Flight(origin, destination, departureDateTime, arrivalDateTime, carrier, price));
            }

            FlightService flightService = new FlightService(flights);

            // Print minimum flight times
            Map<String, Duration> minFlightTimes = flightService.getMinFlightTimePerCarrier();
            System.out.println("Minimum flight time for each carrier:");
            minFlightTimes.forEach((carrier, duration) ->
                    System.out.printf("Carrier: %s, Minimum Flight Time: %d hours %d minutes%n",
                            carrier, duration.toHours(), duration.toMinutesPart())
            );

            // Print price difference
            double priceDifference = flightService.getPriceDifference();
            System.out.printf("Difference between average price and median: %.2f%n", priceDifference);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
