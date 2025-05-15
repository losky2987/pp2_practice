package losky2987.pp2_practice.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Gate {
    private final String number;
    private final String flightNumber;
    private final LocalTime boardingTime;

    public Gate(String number, String flightNumber, LocalTime boardingTime) {
        this.number = number;
        this.flightNumber = flightNumber;
        this.boardingTime = boardingTime;
    }

    private Gate(GateBuilder gateBuilder) {
        this.number = gateBuilder.number;
        this.flightNumber = gateBuilder.flightNumber;
        this.boardingTime = gateBuilder.boardingTime;
    }

    public static class GateBuilder {
        private String number;
        private String flightNumber;
        private LocalTime boardingTime;
        public GateBuilder setNumber(String number) {
            this.number = number;
            return this;
        }
        public GateBuilder setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }
        public GateBuilder setBoardingTime(LocalTime boardingTime) {
            this.boardingTime = boardingTime;
            return this;
        }
        public Gate build() {
            return new Gate(this);
        }
    }

    public String getNumber() {
        return number;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public LocalTime getBoardingTime() {
        return boardingTime;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "number='" + number + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", boardingTime=" + boardingTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Gate gate = (Gate) o;
        return Objects.equals(number, gate.number) && Objects.equals(flightNumber, gate.flightNumber) && Objects.equals(boardingTime, gate.boardingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, flightNumber, boardingTime);
    }
}
