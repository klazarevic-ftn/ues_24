package one.noic.ues_24;

import io.minio.errors.*;
import one.noic.ues_24.model.ESGroup;
import one.noic.ues_24.model.ESPost;
import one.noic.ues_24.model.Group;
import one.noic.ues_24.model.Post;
import one.noic.ues_24.service.GroupService;
import one.noic.ues_24.service.PostService;
import one.noic.ues_24.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeData {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PostService postService;

    @Autowired
    private StorageService storageService;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("init_data.sql"));
        resourceDatabasePopulator.execute(dataSource);

        resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("stored_procedures.sql"));
        resourceDatabasePopulator.setSeparator("$$");
        resourceDatabasePopulator.execute(dataSource);

        elasticsearchOperations.indexOps(ESGroup.class).delete();
        elasticsearchOperations.indexOps(ESPost.class).delete();

        List<Group> groups = groupService.getAll();
        List<ESGroup> esGroups = new ArrayList<>();

        for (Group group : groups) {
            ESGroup esGroup = new ESGroup();
            esGroup.id = group.getId();
            esGroup.name = group.getName();
            esGroup.description = group.getDescription();
            esGroup.rules = group.getRules();

            esGroup.numberOfPosts = groupService.getNumberOfPosts(group.getId());
            esGroup.averageLikes = groupService.getAverageReact(group.getId());
            System.out.println("AVG: " + esGroup.averageLikes);
            esGroup.fileContent = storageService.getFileText("group", group.getId());

            esGroups.add(esGroup);
        }

        elasticsearchOperations.save(esGroups);

        List<Post> posts = postService.getAll();
        List<ESPost> esPosts = new ArrayList<>();

        for (Post post : posts) {
            ESPost esPost = new ESPost();
            esPost.id = post.getId();
            esPost.title = post.getTitle();
            esPost.fullContent = post.getContent();

            esPost.numberOfLikes = postService.getPostLikeCount(post.getId());
            esPost.numberOfComments = postService.getPostCommentCount(post.getId());
            esPost.commentContent = postService.getPostCommentContent(post.getId());
            esPost.fileContent = storageService.getFileText("post", post.getId());

            esPosts.add(esPost);
        }

        elasticsearchOperations.save(esPosts);
    }
}
