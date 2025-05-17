package losky2987.pp2_practice.dto;

import jakarta.validation.constraints.NotNull;
import losky2987.pp2_practice.domain.Flight;

import java.util.List;
import java.util.Objects;

public class GateInfo {
    @NotNull private String number;
    @NotNull private List<Flight> flights;

    public GateInfo(String number, List<Flight> flights) {
        this.number = number;
        this.flights = flights;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GateInfo gateInfo = (GateInfo) o;
        return Objects.equals(number, gateInfo.number) && Objects.equals(flights, gateInfo.flights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, flights);
    }

    @Override
    public String toString() {
        return "GateInfo{" +
                "gateName='" + number + '\'' +
                ", flights=" + flights +
                '}';
    }
}
