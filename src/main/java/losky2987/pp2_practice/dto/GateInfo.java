package losky2987.pp2_practice.dto;

import jakarta.validation.constraints.NotNull;
import losky2987.pp2_practice.domain.Flight;

import java.util.List;
import java.util.Objects;

public class GateInfo {
    @NotNull private String number;
    @NotNull private Flight flight;

    public GateInfo(String number, Flight flight) {
        this.number = number;
        this.flight = flight;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GateInfo gateInfo = (GateInfo) o;
        return Objects.equals(number, gateInfo.number) && Objects.equals(flight, gateInfo.flight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, flight);
    }

    @Override
    public String toString() {
        return "GateInfo{" +
                "number='" + number + '\'' +
                ", flight=" + flight +
                '}';
    }
}
