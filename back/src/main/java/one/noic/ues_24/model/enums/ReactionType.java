package one.noic.ues_24.model.enums;

public enum ReactionType {
    LIKE(1),
    DISLIKE(-1),
    HEART(5);

    public final Integer value;

    ReactionType(final Integer newValue) {
        value = newValue;
    }

    public Integer getValue() { return value; }

    public static ReactionType fromValue(Integer value) {
        for (ReactionType type : ReactionType.values()) {
            if (type.value.equals(value)) { // Use .equals() for Integer comparison
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
