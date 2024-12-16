package one.noic.ues_24.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.noic.ues_24.controller.dto.reaction.ReactionDto;
import one.noic.ues_24.model.enums.ReactionType;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Reaction {

    public Reaction(ReactionDto dto) {
        this.reactionType = dto.reactionType;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Convert(converter = ReactionTypeConverter.class)
    private ReactionType reactionType;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP()")
    private LocalDateTime creationDate;
}
