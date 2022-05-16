package com.michalzero.searoutesanalyzer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeaRoute {
    private String routeId;
    private String vesselId;
    private String fromPort;
    private String toPort;
    private Double legDuration;
    private Double approxDistance;
    private Double averageSpeed;
    private Long count;
}
