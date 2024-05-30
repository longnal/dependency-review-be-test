package com.example.restapi.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NumericBooleanType implements AttributeConverter<Boolean, Short> {

    @Override
    public Short convertToDatabaseColumn(Boolean value) {
        return (short) (value ? 1 : 0);
    }

    @Override
    public Boolean convertToEntityAttribute(Short dbData) {
        return dbData == 1;
    }
}