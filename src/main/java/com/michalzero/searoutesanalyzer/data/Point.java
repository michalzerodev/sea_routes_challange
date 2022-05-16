package com.michalzero.searoutesanalyzer.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    private Float longitude;
    private Float latitude;

    Point(String longitude, String latitude) {
        this.longitude = Float.parseFloat(longitude);
        this.latitude = Float.parseFloat(latitude);
    }
}
