package one.noic.ues_24.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import one.noic.ues_24.controller.dto.group.CreateGroupDto;
import one.noic.ues_24.model.ESGroup;
import one.noic.ues_24.model.Group;
import one.noic.ues_24.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public GroupService(GroupRepository groupRepository, ElasticsearchOperations elasticsearchOperations) {
        this.groupRepository = groupRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void createGroup(CreateGroupDto group) {
        ESGroup newGroup = new ESGroup();
        newGroup.id = org.elasticsearch.common.UUIDs.base64UUID();
        newGroup.name = group.name;
        newGroup.description = group.description;
        newGroup.rules = group.rules;
        newGroup.averageLikes = 0D;
        newGroup.fileContent = "Test";
        newGroup.numberOfPosts = 0L;
        this.elasticsearchOperations.save(newGroup);
//        groupRepository.save(newGroup);
    }
}
