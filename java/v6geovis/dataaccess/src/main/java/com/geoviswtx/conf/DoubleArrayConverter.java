package com.geoviswtx.conf;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class DoubleArrayConverter implements AttributeConverter<Double[], String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Double[] attribute) {
        if (attribute == null) {
            return null;
        }

        return String.join(DELIMITER, Arrays.stream(attribute).map(String::valueOf).toArray(String[]::new));
    }

    @Override
    public Double[] convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

//        dbData = dbData.replace("{", "")
//                .replace("}", "");

        String[] values = dbData.split(DELIMITER);

        return Arrays.stream(values).map(Double::valueOf).toArray(Double[]::new);
    }
}

