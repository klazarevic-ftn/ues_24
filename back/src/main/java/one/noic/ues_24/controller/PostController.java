package one.noic.ues_24.controller;

import io.minio.errors.*;
import one.noic.ues_24.controller.dto.post.PostDto;
import one.noic.ues_24.model.Post;
import one.noic.ues_24.service.PostService;
import org.simpleframework.xml.Path;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public Integer createPost(@RequestPart("post") PostDto postDto, @RequestPart("file") MultipartFile file) throws IOException {
        return postService.createPost(postDto, file);
    }

    @GetMapping("group/{id}")
    public Integer getGroupIdById(@PathVariable(value = "id") Integer id) {
        return postService.getGroupIdById(id);
    }

    @GetMapping("{id}")
    public Optional<Post> getPostById(@PathVariable(value = "id") Integer id) {
        return postService.getById(id);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable(value = "id") Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        this.postService.deletePost(id);
    }
}