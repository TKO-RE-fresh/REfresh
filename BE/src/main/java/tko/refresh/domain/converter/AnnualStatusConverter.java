package tko.refresh.domain.converter;

import javax.persistence.AttributeConverter;

import tko.refresh.domain.enu.AnnualStatus;

public class AnnualStatusConverter implements AttributeConverter<AnnualStatus, String> {
    @Override
    public String convertToDatabaseColumn(AnnualStatus attribute) {
        return attribute.getLabel();
    }

    @Override
    public AnnualStatus convertToEntityAttribute(String dbData) {
        return AnnualStatus.getEnumByLabel(dbData);
    }

}
