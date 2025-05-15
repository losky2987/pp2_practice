package losky2987.pp2_practice.dto;

import jakarta.validation.constraints.NotNull;
import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;

import java.time.LocalTime;

public class GateInfo {
    @NotNull private String gateName;
    @NotNull private String flightNumber;
    @NotNull private String destination;
    @NotNull private LocalTime departureTime;
}
