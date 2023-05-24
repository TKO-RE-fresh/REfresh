package tko.refresh.domain.enu;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AnnualType {
    ANNUAL_LEAVE("ANNUAL_LEAVE", 1),
    MORNING_HALF("MORNING_HALF", 2),
    AFTERNOON_HALF("AFTERNOON_HALF", 3);
    private static final Map<Integer, AnnualType> LABEL_LOOKUP = new HashMap<>();

    static {
        for (AnnualType annualType : values()) {
            LABEL_LOOKUP.put(annualType.getLabel(), annualType);
        }
    }

    private final String code;
    private final int label;

    public String getCode() {
        return code;
    }

    public int getLabel() {
        return label;
    }
    public static AnnualType getEnumByLabel(int label) {
        if(LABEL_LOOKUP.containsKey(label)) {
            return LABEL_LOOKUP.get(label);
        }

        throw new IllegalArgumentException("Invalid label: " + label);
    }


}
