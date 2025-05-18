package losky2987.pp2_practice.dto;

import jakarta.validation.constraints.NotNull;
import losky2987.pp2_practice.domain.Flight;

import java.util.List;
import java.util.Objects;

public class GateInfo {
    @NotNull private String number;

    public GateInfo(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GateInfo gateInfo = (GateInfo) o;
        return Objects.equals(number, gateInfo.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public String toString() {
        return "GateInfo{" +
                "number='" + number + '\'' +
                '}';
    }
}
