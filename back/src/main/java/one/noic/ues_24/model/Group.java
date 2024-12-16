package one.noic.ues_24.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.noic.ues_24.controller.dto.group.CreateGroupDto;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Integer id;

    @Column(unique = true)
    private String name;

    private String description;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP()")
    private LocalDateTime creationDate;

    @Column(nullable = false, columnDefinition = "BIT DEFAULT 0")
    private boolean isSuspended;

    private String suspendedReason;

    private String rules;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @JoinColumn(name = "group_id")
    public List<Post> posts = new ArrayList<>();
}
