package tko.refresh.util.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import tko.refresh.domain.enu.RoleType;

@Converter(autoApply = true)
public class RoleTypeConverter implements AttributeConverter<RoleType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RoleType attribute) {
        return attribute.getLabel();
    }

    @Override
    public RoleType convertToEntityAttribute(Integer dbData) {
        return RoleType.getEnumByLabel(dbData);
    }
}
