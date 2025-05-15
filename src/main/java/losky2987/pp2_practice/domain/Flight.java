package losky2987.pp2_practice.domain;

import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;
import java.util.Objects;

@Table
public class Flight {
    private final String number;
    private final String destination;
    private final LocalTime departureTime;

    public Flight(String number, String destination, LocalTime departureTime) {
        this.number = number;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    private Flight(FlightBuilder flightBuilder) {
        this.number = flightBuilder.number;
        this.destination = flightBuilder.destination;
        this.departureTime = flightBuilder.departureTime;
    }

    public static class FlightBuilder {
        private String number;
        private String destination;
        private LocalTime departureTime;
        public FlightBuilder setNumber(String number) {
            this.number = number;
            return this;
        }
        public FlightBuilder setDestination(String destination) {
            this.destination = destination;
            return this;
        }
        public FlightBuilder setDepartureTime(LocalTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }
        public Flight build() {
            return new Flight(number, destination, departureTime);
        }
    }

    public String getNumber() {
        return number;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(number, flight.number) && Objects.equals(destination, flight.destination) && Objects.equals(departureTime, flight.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, destination, departureTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "number='" + number + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                '}';
    }
}
