package com.michalzero.searoutesanalyzer.data;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class SeaRouteCsvBean {
    private static final Long MINIMAL_VESSEL_DATA_INTERVAL = 900000L; // 15 min

    @CsvBindByName
    private String id;

    @CsvBindByName(column = "from_seq")
    private Integer fromSeq;

    @CsvBindByName(column = "to_seq")
    private Integer toSeq;

    @CsvBindByName(column = "from_port")
    private String fromPort;

    @CsvBindByName(column = "to_port")
    private String toPort;

    @CsvBindByName(column = "leg_duration")
    private Long legDuration;

    @CsvBindByName
    private Long count;

    @CsvCustomBindByName(converter = VesselDataConverter.class)
    private List<VesselData> points;

    boolean isDataRepresentative() {
        return IntStream.range(0, this.points.size() - 1)
                .noneMatch(i -> Math.abs(this.points.get(i + 1).getTimestamp() - this.points.get(i).getTimestamp()) > MINIMAL_VESSEL_DATA_INTERVAL);
    }

}