package tko.refresh.util.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import tko.refresh.domain.enu.AnnualStatus;
@Converter(autoApply = true)
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
