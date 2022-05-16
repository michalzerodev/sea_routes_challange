package com.michalzero.searoutesanalyzer.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.AbstractBeanField;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VesselDataConverter extends AbstractBeanField<List<VesselData>, Integer> {

    @Override
    protected List<VesselData> convert(String rawData) {
        List<VesselData> parsedData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            parsedData = objectMapper.readValue(correctBrokenData(rawData), new TypeReference<List<List<String>>>() {
                    })
                    .stream()
                    .map(data -> new VesselData(data.get(0), data.get(1), data.get(2)))
                    .sorted(Comparator.comparingLong(VesselData::getTimestamp))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return parsedData;
    }

    private String correctBrokenData(String value) {
        if (value.contains("] [")) {
            value = value.replace("] [", "], [");
        }
        return value;
    }
}

