package losky2987.pp2_practice.domain;

import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Table
public class Gate {
    private final String number;
    private final List<Flight> flights;

    public Gate(String number, List<Flight> flights) {
        this.number = number;
        this.flights = flights;
    }

    private Gate(GateBuilder gateBuilder) {
        this.number = gateBuilder.number;
        this.flights = gateBuilder.flights;
    }

    public static class GateBuilder {
        private String number;
        private List<Flight> flights;
        public GateBuilder setNumber(String number) {
            this.number = number;
            return this;
        }
        public GateBuilder setFlights(List<Flight> flights) {
            this.flights = flights;
            return this;
        }
        public Gate build() {
            return new Gate(this);
        }
    }

    public String getNumber() {
        return number;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Gate gate = (Gate) o;
        return Objects.equals(number, gate.number) && Objects.equals(flights, gate.flights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, flights);
    }

    @Override
    public String toString() {
        return "Gate{" +
                "number='" + number + '\'' +
                ", flights=" + flights +
                '}';
    }
}
