package one.noic.ues_24.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private Boolean approved;
    private LocalDateTime createdAt;
    private LocalDateTime at;
}
