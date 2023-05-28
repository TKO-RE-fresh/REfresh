package tko.refresh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;
import tko.refresh.domain.enu.MemberStatus;

@Converter(autoApply = true)
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
