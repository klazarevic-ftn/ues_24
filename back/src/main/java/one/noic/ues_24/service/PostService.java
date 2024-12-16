package one.noic.ues_24.service;

import io.minio.errors.*;
import one.noic.ues_24.controller.dto.post.PostDto;
import one.noic.ues_24.model.ESGroup;
import one.noic.ues_24.model.ESPost;
import one.noic.ues_24.repository.PostRepository;
import one.noic.ues_24.model.Post;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final StorageService storageService;

    @Autowired
    public PostService(PostRepository postRepository, ElasticsearchOperations elasticsearchOperations, StorageService storageService) {
        this.postRepository = postRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.storageService = storageService;
    }

    public Integer createPost(PostDto dto, MultipartFile file) throws IOException {
        Post newPost = new Post(dto);
        newPost = this.postRepository.save(newPost);
        storageService.uploadFile("post", newPost.getId() + ".pdf", file.getInputStream(), "application/pdf");

        File convertedFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(convertedFile);

        ESPost newESPost = new ESPost(dto);
        newESPost.id = newPost.getId();

        try(PDDocument document = Loader.loadPDF(convertedFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            newESPost.fileContent = stripper.getText(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.elasticsearchOperations.save(newESPost);

        ESGroup esGroup = elasticsearchOperations.get(dto.groupId + "", ESGroup.class);
        esGroup.numberOfPosts += 1;
        elasticsearchOperations.save(esGroup);

        return newPost.getId();
    }

    public Optional<Post> getById(Integer id) {
        return postRepository.findById(id);
    }

    public void deletePost(Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        this.postRepository.deleteById(id);
        this.elasticsearchOperations.delete(id + "", ESPost.class);
        this.storageService.deleteFile("post", id + ".pdf");
    }
}
