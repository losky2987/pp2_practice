package losky2987.pp2_practice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table
public class Gate {
    @Id
    private final Long id;
    private final String number;

    @PersistenceCreator
    public Gate(Long id, String number) {
        this.number = number;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Gate gate = (Gate) o;
        return Objects.equals(id, gate.id) && Objects.equals(number, gate.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "Gate{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
