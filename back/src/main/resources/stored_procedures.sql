CREATE PROCEDURE post_count (IN in_group_id INT, OUT num_of_posts INT)
BEGIN
    SELECT COUNT(`post`.id) INTO num_of_posts
        FROM `group` JOIN `post` ON `group`.id = `post`.group_id
        WHERE `group`.id = in_group_id;
END;
