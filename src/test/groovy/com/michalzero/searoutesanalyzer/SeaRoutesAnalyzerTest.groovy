package com.michalzero.searoutesanalyzer

import com.michalzero.searoutesanalyzer.data.SeaRoutesDataParser
import spock.lang.Specification

class SeaRoutesAnalyzerTest extends Specification {

    SeaRoutesAnalyzer seaRoutesAnalyzer = new SeaRoutesAnalyzer()
    SeaRoutesDataParser seaRoutesDataParser = new SeaRoutesDataParser()

    def "should find the most representative route in given CSV data"() {
        given:
          String filePath = "src/test/resources/DEBRV_DEHAM_historical_routes.csv"
          List<SeaRoute> seaRoutes = seaRoutesDataParser.parseCsvDataFromFile(filePath)
        when:
          SeaRoute result = seaRoutesAnalyzer.findMostRepresentativeRoute(seaRoutes)
        then:
          System.out.println("\n \nThe most representative route is: \n" + result.toString() + "\n \n")
    }
}