package one.noic.ues_24.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.noic.ues_24.controller.dto.post.PostDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Post {

    public Post(PostDto dto) {
        this.title = dto.title;
        this.content = dto.content;
    }

    public Post(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP()")
    private LocalDateTime creationDate;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "post_id")
    public List<Comment> comments = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "post_id")
    private List<Reaction> reactions;
}
