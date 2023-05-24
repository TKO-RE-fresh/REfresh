package tko.refresh.domain.converter;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import tko.refresh.domain.enu.AnnualType;
@Converter(autoApply = true)
public class AnnualTypeConverter implements AttributeConverter<AnnualType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AnnualType attribute) {
        return attribute.getLabel();
    }

    @Override
    public AnnualType convertToEntityAttribute(Integer dbData) {
        return AnnualType.getEnumByLabel(dbData);
    }

}
