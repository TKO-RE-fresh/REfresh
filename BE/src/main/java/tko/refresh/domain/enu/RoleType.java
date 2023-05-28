package tko.refresh.domain.enu;

import java.util.HashMap;
import java.util.Map;

public enum RoleType {

    ADMIN("admin", 0),
    MEMBER("member", 1);

    private String value;
    private int label;
    private static final Map<Integer, RoleType> map = new HashMap<>();
    static {
        map.put(0, ADMIN);
        map.put(1, MEMBER);
    }



    RoleType(String value, int label) {
        this.value = value;
        this.label = label;
    }

    public int getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static RoleType getEnumByLabel(int label) {
        return map.get(label);
    }
}
