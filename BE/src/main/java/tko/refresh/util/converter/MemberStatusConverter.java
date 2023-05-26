package tko.refresh.util.converter;

import javax.persistence.AttributeConverter;

import tko.refresh.domain.enu.MemberStatus;

public class MemberStatusConverter implements AttributeConverter<MemberStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MemberStatus attribute) {
        return attribute.getLabel();
    }

    @Override
    public MemberStatus convertToEntityAttribute(Integer dbData) {
        return MemberStatus.getEnumByLabel(dbData);
    }

}
