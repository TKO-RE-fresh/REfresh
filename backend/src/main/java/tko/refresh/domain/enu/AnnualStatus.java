package tko.refresh.domain.enu;

import java.util.HashMap;
import java.util.Map;

public enum AnnualStatus {
    CANCEL("CANCEL", "취소"),
    WAITING("WAITING", "대기"),
    AGREE("AGREE", "승인");
    private static final Map<String, AnnualStatus> LABEL_LOOKUP = new HashMap<>();

    static {
        for (AnnualStatus annualStatus : values()) {
            LABEL_LOOKUP.put(annualStatus.getLabel(), annualStatus);
        }
    }


    private final String code;
    private final String label;

    AnnualStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }


    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static AnnualStatus getEnumByLabel(String label) {
        if(LABEL_LOOKUP.containsKey(label)) {
            return LABEL_LOOKUP.get(label);
        }
        throw new IllegalArgumentException("Invalid label: " + label);
    }
}
