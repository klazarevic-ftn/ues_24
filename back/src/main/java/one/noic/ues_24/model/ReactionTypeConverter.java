package one.noic.ues_24.model;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import one.noic.ues_24.model.enums.ReactionType;

@Converter(autoApply = true) // Automatically applies this converter wherever ReactionType is used
public class ReactionTypeConverter implements AttributeConverter<ReactionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReactionType reactionType) {
        if (reactionType == null) {
            return null;
        }
        return reactionType.getValue(); // Store the numeric value
    }

    @Override
    public ReactionType convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }
        return ReactionType.fromValue(value); // Convert back to the enum
    }
}