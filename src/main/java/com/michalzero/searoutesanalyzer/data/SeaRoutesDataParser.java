package com.michalzero.searoutesanalyzer.data;

import com.michalzero.searoutesanalyzer.SeaRoute;
import com.michalzero.searoutesanalyzer.SeaRouteMapper;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class SeaRoutesDataParser {
    public List<SeaRoute> parseCsvDataFromFile(String filePath) throws FileNotFoundException {
        return new CsvToBeanBuilder<SeaRouteCsvBean>(new FileReader(filePath))
                .withType(SeaRouteCsvBean.class)
                .withIgnoreEmptyLine(true)
                .withSeparator(',')
                .build()
                .parse()
                .stream()
                .filter(SeaRouteCsvBean::isDataRepresentative)
                .map(SeaRouteMapper.MAPPER::toSeaRoute)
                .collect(Collectors.toList());
    }

}
