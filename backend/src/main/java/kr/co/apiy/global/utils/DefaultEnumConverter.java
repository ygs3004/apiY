package kr.co.apiy.global.utils;

import jakarta.persistence.AttributeConverter;

public class DefaultEnumConverter<E extends Enum<E>> implements AttributeConverter<E, String>{

    Class<E> enumClass;

    DefaultEnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E enumType) {
        return enumType.name();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return E.valueOf(enumClass, dbData);
    }

}
