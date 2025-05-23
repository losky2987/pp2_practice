package losky2987.pp2_practice.controller.dto;

import jakarta.validation.constraints.NotNull;
import losky2987.pp2_practice.domain.Flight;

import java.util.Objects;

public class GateInfoDTO {
    @NotNull private String number;
    private Flight flight;

    public GateInfoDTO(String number, Flight flight) {
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
        GateInfoDTO gateInfoDTO = (GateInfoDTO) o;
        return Objects.equals(number, gateInfoDTO.number) && Objects.equals(flight, gateInfoDTO.flight);
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
