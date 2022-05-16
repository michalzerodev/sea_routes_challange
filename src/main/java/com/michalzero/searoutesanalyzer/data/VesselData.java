package com.michalzero.searoutesanalyzer.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VesselData {

    private Point point;
    private Long timestamp;

    public VesselData(String longitude, String latitude, String timestamp) {
        this.point = new Point(longitude, latitude);
        this.timestamp = Long.parseLong(timestamp);
    }

}
