package com.michalzero.searoutesanalyzer;

import com.michalzero.searoutesanalyzer.data.CoordinatesToDistanceConverter;
import com.michalzero.searoutesanalyzer.data.Point;
import com.michalzero.searoutesanalyzer.data.SeaRouteCsvBean;
import com.michalzero.searoutesanalyzer.data.VesselData;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.IntStream;

@Mapper
public interface SeaRouteMapper {

    SeaRouteMapper MAPPER = Mappers.getMapper(SeaRouteMapper.class);

    @Mapping(target = "approxDistance", ignore = true)
    @Mapping(target = "vesselId", source = "id")
    @Mapping(target = "averageSpeed", ignore = true)
    @Mapping(target = "routeId", expression = "java(java.lang.String.join(\"_\", seaRouteCsvBean.getFromSeq().toString(), seaRouteCsvBean.getToSeq().toString()))")
    SeaRoute toSeaRoute(SeaRouteCsvBean seaRouteCsvBean);

    @AfterMapping
    default void calculateApproximateDistance(SeaRouteCsvBean seaRouteCsvBean, @MappingTarget SeaRoute seaRoute) {
        seaRoute.setApproxDistance(IntStream.range(0, seaRouteCsvBean.getPoints().size() - 1)
                .mapToDouble(i -> getLegDistance(seaRouteCsvBean.getPoints(), i))
                .sum());
        seaRoute.setAverageSpeed((seaRoute.getApproxDistance() / seaRouteCsvBean.getLegDuration() * 3600000) * 0.539956803);
    }

    default double getLegDistance(List<VesselData> points, Integer i) {
        int pointAIndex = i;
        int pointBIndex = i + 1;
        Point pointA = points.get(pointAIndex).getPoint();
        Point pointB = points.get((pointBIndex)).getPoint();
        return CoordinatesToDistanceConverter.getApproximateDistanceBetween(pointA, pointB);
    }
}
