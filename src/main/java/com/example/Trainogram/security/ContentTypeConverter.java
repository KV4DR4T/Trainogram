package com.example.Trainogram.security;

import com.example.Trainogram.model.ContentType;
import com.example.Trainogram.model.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public class ContentTypeConverter {
    @Converter(autoApply = true)
    public static  class FieldConverter implements AttributeConverter<ContentType,Integer> {

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
