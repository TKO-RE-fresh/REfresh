package tko.refresh.domain.enu;

public enum RedisData {
    MEMBER_ID("memberId"),
    MEMBER_NAME("memberName"),
    MEMBER_AUTH("memberAuth"),
    MEMBER_DEPT("memberDept");

    private final String name;

    RedisData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
