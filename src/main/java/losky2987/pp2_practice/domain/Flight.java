package losky2987.pp2_practice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;
import java.util.Objects;

@Table
public class Flight {
    @Id
    private final String number;
    private final String destination;
    private final LocalTime departureTime;
    private final String gateNumber;

    public Flight(String number, String destination, LocalTime departureTime, String gateNumber) {
        this.number = number;
        this.destination = destination;
        this.departureTime = departureTime;
        this.gateNumber = gateNumber;
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

    public String getGateNumber() {
        return gateNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(number, flight.number) && Objects.equals(destination, flight.destination) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(gateNumber, flight.gateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, destination, departureTime, gateNumber);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "number='" + number + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", gate_number='" + gateNumber + '\'' +
                '}';
    }
}
