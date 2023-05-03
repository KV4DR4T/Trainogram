package com.example.trainogram.security;

import com.example.trainogram.model.ContentType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public class ContentTypeConverter {
    @Converter(autoApply = true)
    public static class FieldConverter implements AttributeConverter<ContentType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(ContentType type) {
            return type.getId();
        }

        @Override
        public ContentType convertToEntityAttribute(Integer id) {
            return ContentType.typeFromId(id);
        }
    }
}
