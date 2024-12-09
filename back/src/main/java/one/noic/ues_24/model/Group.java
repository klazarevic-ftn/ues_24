package one.noic.ues_24.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.noic.ues_24.controller.dto.group.CreateGroupDto;

import java.time.LocalDateTime;

@Data
@Entity(name = "`group`")
@NoArgsConstructor
public class Group {

    public Group(CreateGroupDto dto) {
        this.name = dto.name;
        this.description = dto.description;
        this.creationDate = LocalDateTime.now();
        this.isSuspended = false;
        this.rules = dto.rules;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private String description;

    private LocalDateTime creationDate;

    private boolean isSuspended;

    private String suspendedReason;

    private String rules;
}
