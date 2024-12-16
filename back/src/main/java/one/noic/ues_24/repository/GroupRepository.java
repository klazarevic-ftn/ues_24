package one.noic.ues_24.repository;

import one.noic.ues_24.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Procedure("num_post_per_group")
    Long getNumberOfPostsById(Integer id);

    @Procedure("avg_reaction")
    Double getAverageReact(Integer id);

}
