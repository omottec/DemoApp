package com.omottec.demoapp.dao;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by qinbingbing on 10/04/2017.
 */

public class NoteTypeConverter implements PropertyConverter<NoteType, String> {
    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}
