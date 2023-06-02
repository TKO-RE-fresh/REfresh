package tko.refresh.domain.enu;

import java.util.HashMap;
import java.util.Map;


public enum MemberStatus {
    IN_USE("사용", 0),
    NOT_IN_USE("휴면", 1);

    private final String code;
    private final int label;

    private static final Map<Integer, MemberStatus> LABEL_LOOKUP = new HashMap<>();

    static {
        for (MemberStatus memberStatus : values()) {
            LABEL_LOOKUP.put(memberStatus.getLabel(), memberStatus);
        }
    }
    MemberStatus(String code, int label) {
        this.code = code;
        this.label = label;
    }


    public String getCode() {
        return code;
    }

    public int getLabel() {
        return label;
    }

    public static MemberStatus getEnumByLabel(int label) {
        if(LABEL_LOOKUP.containsKey(label)) {
            return LABEL_LOOKUP.get(label);
        }
        throw new IllegalArgumentException("Invalid label: " + label);
    }



}
