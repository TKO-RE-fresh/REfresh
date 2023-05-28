package tko.refresh.domain.enu;

public enum RoleType {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
