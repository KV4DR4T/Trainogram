package com.example.trainogram.security;

import com.example.trainogram.model.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public class RoleConverter {
    @Converter(autoApply = true)
    public static class FieldConverter implements AttributeConverter<Role, Integer> {

        @Override
        public Integer convertToDatabaseColumn(Role authority) {
            return authority.getId();
        }

        @Override
        public Role convertToEntityAttribute(Integer id) {
            return Role.roleFromId(id);
        }
    }
}
