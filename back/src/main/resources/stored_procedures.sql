DROP PROCEDURE IF EXISTS y.post_count;
$$
DROP PROCEDURE IF EXISTS y.avg_likes_per_group;
$$
DROP PROCEDURE IF EXISTS y.post_comments;
$$
DROP PROCEDURE IF EXISTS y.post_comment_count;
$$
DROP PROCEDURE IF EXISTS y.get_group_id_by_post_id
$$
DROP PROCEDURE IF EXISTS y.number_of_likes;
$$
CREATE PROCEDURE y.post_count(IN in_group_id INT, OUT num_of_posts INT)
BEGIN
    SELECT COUNT(`post`.id) INTO num_of_posts
    FROM `group` JOIN `post` ON `group`.id = `post`.group_id
    WHERE `group`.id = in_group_id;
END;
$$
CREATE PROCEDURE y.avg_likes_per_group(IN in_group_id INT, OUT avg_likes DOUBLE)
BEGIN
    select avg(avg_reaction) from (
        select `group`.id as inner_group_id, `post`.id, avg(reaction.reaction_type) as avg_reaction
        from `group` join `post` on `group`.id = `post`.group_id
            join reaction on `reaction`.post_id = `post`.id group by `post`.id) as INNER_TABLE
                                             where inner_group_id = in_group_id
                                             group by inner_group_id;
END;
$$
CREATE PROCEDURE y.post_comments(IN in_post_id INT, OUT comment_text TEXT)
BEGIN
    select GROUP_CONCAT(text) into comment_text from `post` join comment on `post`.id = comment.post_id where `post`.id = in_post_id;
END;
$$
CREATE PROCEDURE y.post_comment_count(IN in_post_id INT, OUT count_of_comments INT)
BEGIN
    select count(comment.id) INTO count_of_comments from `post` join comment on `post`.id = comment.post_id where `post`.id = in_post_id;
END;
$$
CREATE PROCEDURE y.get_group_id_by_post_id(IN in_post_id INT, OUT out_group_id INT)
BEGIN
    select `post`.group_id INTO out_group_id from `post` where `post`.id = in_post_id;
END;
$$
CREATE PROCEDURE y.number_of_likes(IN in_post_id INT, OUT out_like_count LONG)
BEGIN
    select count(reaction_type) INTO out_like_count
    from reaction
    where (reaction.post_id = in_post_id or
           reaction.comment_id in (select post.id from post
                                    join comment on post.id = comment.post_id
                                                where post.id = in_post_id))
      and reaction_type > 0;
END;