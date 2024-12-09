package one.noic.ues_24.service;

import one.noic.ues_24.controller.dto.group.CreateGroupDto;
import one.noic.ues_24.model.ESGroup;
import one.noic.ues_24.model.Group;
import one.noic.ues_24.repository.GroupRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final StorageService storageService;

    @Autowired
    public GroupService(GroupRepository groupRepository, ElasticsearchOperations elasticsearchOperations, StorageService storageService) {
        this.groupRepository = groupRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.storageService =  storageService;
    }

    public void createGroup(CreateGroupDto group, MultipartFile file) throws IOException {
        Group newGroup = new Group(group);
        newGroup = this.groupRepository.save(newGroup);
        storageService.uploadFile("ues24", newGroup.getId() + "", file.getInputStream(), "application/pdf");

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
    }
}
