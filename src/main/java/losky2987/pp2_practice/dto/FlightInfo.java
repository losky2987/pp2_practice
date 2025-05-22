package losky2987.pp2_practice.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Objects;

public class FlightInfo {
    private String flightNumber;
    private String destination;
    @DateTimeFormat(pattern = "HH:mm") private LocalTime departureTime;
    private String gateNumber;

    public FlightInfo(String flightNumber, String destination, LocalTime departureTime, String gateNumber) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureTime = departureTime;
        this.gateNumber = gateNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FlightInfo that = (FlightInfo) o;
        return Objects.equals(flightNumber, that.flightNumber) && Objects.equals(destination, that.destination) && Objects.equals(departureTime, that.departureTime) && Objects.equals(gateNumber, that.gateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, destination, departureTime, gateNumber);
    }

    @Override
    public String toString() {
        return "FlightInfo{" +
                "flightNumber='" + flightNumber + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", gateNumber='" + gateNumber + '\'' +
                '}';
    }
}
