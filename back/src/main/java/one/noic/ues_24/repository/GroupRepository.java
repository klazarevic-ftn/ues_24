package one.noic.ues_24.repository;

import one.noic.ues_24.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Procedure("post_count")
    Long getNumberOfPostsById(Integer id);

    @Procedure("avg_likes_per_group")
    Double getAverageReact(Integer id);

}
