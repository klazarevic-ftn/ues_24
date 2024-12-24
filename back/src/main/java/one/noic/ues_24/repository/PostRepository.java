package one.noic.ues_24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import one.noic.ues_24.model.Post;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.web.bind.annotation.PathVariable;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Procedure("post_comments")
    String getCommentContent(Integer id);

    @Procedure("post_comment_count")
    Integer getPostCommentCount(Integer id);

    @Procedure("get_group_id_by_post_id")
    Integer getGroupIdById(Integer id);

    @Procedure("number_of_likes")
    Long getPostLikeCount(Integer id);
}
