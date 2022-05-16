package com.michalzero.searoutesanalyzer;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeaRoutesAnalyzer {

    public SeaRoute findMostRepresentativeRoute(List<SeaRoute> seaRoutes) {
        Map<String, List<SeaRoute>> routesGroupedByRouteId = seaRoutes.stream()
                .collect(Collectors.groupingBy(SeaRoute::getRouteId));

        Map<String, Double> averageDistancePerRoute = getAverageDistancePerRoute(routesGroupedByRouteId);
        List<SeaRoute> routesClosestToAverageDistance = getRoutesClosestToAverageDistance(routesGroupedByRouteId, averageDistancePerRoute);
        Double averageDistanceOfReducedRoutes = getAverageDistanceOfReducedRoutes(routesClosestToAverageDistance);

        return getRouteClosestToAverageDistanceOfReducedRoutes(routesClosestToAverageDistance, averageDistanceOfReducedRoutes);
    }

    private SeaRoute getRouteClosestToAverageDistanceOfReducedRoutes(List<SeaRoute> routesClosestToAverageDistance, Double averageDistanceOfReducedRoutes) {
        return routesClosestToAverageDistance
                .stream()
                .min(Comparator.comparingDouble(e -> Math.abs(averageDistanceOfReducedRoutes - e.getApproxDistance())))
                .get();
    }

    private double getAverageDistanceOfReducedRoutes(List<SeaRoute> routesClosestToAverageDistance) {
        return routesClosestToAverageDistance
                .stream()
                .mapToDouble(SeaRoute::getApproxDistance)
                .average()
                .getAsDouble();
    }

    private List<SeaRoute> getRoutesClosestToAverageDistance(Map<String, List<SeaRoute>> routesGroupedByRouteId, Map<String, Double> averageDistancePerRoute) {
        return routesGroupedByRouteId
                .values()
                .stream()
                .map(routes -> pickClosestRouteToAverageDistance(averageDistancePerRoute, routes))
                .collect(Collectors.toList());
    }

    private Map<String, Double> getAverageDistancePerRoute(Map<String, List<SeaRoute>> routesGroupedByRouteId) {
        return routesGroupedByRouteId.entrySet()
                .stream()
                .map(e -> new SimpleEntry<>(
                        e.getKey(),
                        e.getValue().stream().mapToDouble(SeaRoute::getApproxDistance)
                                .average()
                                .getAsDouble()
                ))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    private SeaRoute pickClosestRouteToAverageDistance(Map<String, Double> averageDistancePerRoute, List<SeaRoute> e) {
        return e.stream()
                .min(Comparator.comparingDouble(route -> Math.abs(averageDistancePerRoute.get(route.getRouteId()) - route.getApproxDistance())))
                .get();
    }

}
