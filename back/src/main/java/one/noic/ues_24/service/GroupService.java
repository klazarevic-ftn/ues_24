package one.noic.ues_24.service;

import io.minio.errors.*;
import one.noic.ues_24.controller.dto.group.CreateGroupDto;
import one.noic.ues_24.model.ESGroup;
import one.noic.ues_24.model.Group;
import one.noic.ues_24.repository.GroupRepository;
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
public class GroupService {

    private final GroupRepository groupRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final StorageService storageService;

    @Autowired
    public GroupService(GroupRepository groupRepository, ElasticsearchOperations elasticsearchOperations, StorageService storageService) {
        this.groupRepository = groupRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.storageService = storageService;
    }

    public Integer createGroup(CreateGroupDto group, MultipartFile file) throws IOException {
        Group newGroup = new Group(group);
        newGroup = this.groupRepository.save(newGroup);
        storageService.uploadFile("group", newGroup.getId() + ".pdf", file.getInputStream(), "application/pdf");

        File convertedFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(convertedFile);

        ESGroup newESGroup = new ESGroup(group);
        newESGroup.id = newGroup.getId();

        try(PDDocument document = Loader.loadPDF(convertedFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            newESGroup.fileContent = stripper.getText(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.elasticsearchOperations.save(newESGroup);

        return newGroup.getId();
    }

    public void deleteById(Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        this.groupRepository.deleteById(id);
        this.elasticsearchOperations.delete(id + "", ESGroup.class);
        this.storageService.deleteFile("group", id + ".pdf");
    }

    public Optional<Group> getById(Integer id) {
        return groupRepository.findById(id);
    }

    public void updateGroup(Integer id, CreateGroupDto dto, MultipartFile file) throws IOException {
        Optional<Group> optionalGroup = getById(id);
        if(optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            group.setName(dto.name);
            group.setDescription(dto.description);
            group.setRules(dto.rules);

            this.groupRepository.save(group);

            ESGroup esGroup = this.elasticsearchOperations.get(id + "", ESGroup.class);
            esGroup.name = dto.name;
            esGroup.description = dto.description;
            esGroup.rules = dto.rules;

            if(file != null) {
                File convertedFile = File.createTempFile("temp", file.getOriginalFilename());
                file.transferTo(convertedFile);

                try (PDDocument document = Loader.loadPDF(convertedFile)) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    esGroup.fileContent = stripper.getText(document);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            this.elasticsearchOperations.update(esGroup);
        }
    }

    public Long getNumberOfPosts(Integer id) {
        return this.groupRepository.getNumberOfPostsById(id);
    }

    public Double getAverageReact(Integer id) {
        return this.groupRepository.getAverageReact(id);
    }
}
