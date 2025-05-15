package losky2987.pp2_practice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Objects;

public class GateInfoUpdateForm {
    @NotBlank private String gateNumber;
    @NotBlank private String flightNumber;
    @NotNull @DateTimeFormat(pattern = "HH:mm") private LocalTime boardingTime;

    public GateInfoUpdateForm(String gateNumber, String flightNumber, LocalTime boardingTime) {
        this.gateNumber = gateNumber;
        this.flightNumber = flightNumber;
        this.boardingTime = boardingTime;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public LocalTime getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(LocalTime boardingTime) {
        this.boardingTime = boardingTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return "GateInfoUpdateForm{" +
                "gateNumber='" + gateNumber + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", boardingTime=" + boardingTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GateInfoUpdateForm that = (GateInfoUpdateForm) o;
        return Objects.equals(gateNumber, that.gateNumber) && Objects.equals(flightNumber, that.flightNumber) && Objects.equals(boardingTime, that.boardingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gateNumber, flightNumber, boardingTime);
    }
}
