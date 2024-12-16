package one.noic.ues_24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import one.noic.ues_24.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
