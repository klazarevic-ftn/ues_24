package one.noic.ues_24.repository;

import one.noic.ues_24.model.Group;
import one.noic.ues_24.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
}
