package one.noic.ues_24.controller;

import io.minio.errors.*;
import one.noic.ues_24.controller.dto.group.CreateGroupDto;
import one.noic.ues_24.model.Group;
import one.noic.ues_24.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("{id}")
    public Optional<Group> getGroupById(@PathVariable(value = "id") Integer id) {
        return groupService.getById(id);
    }

    @PostMapping("")
    public Integer crateGroup(@RequestPart("group") CreateGroupDto group, @RequestPart("file") MultipartFile file) throws IOException {
        return groupService.createGroup(group, file);
    }

    @DeleteMapping("{id}")
    public void deleteGroup(@PathVariable(value = "id") Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        groupService.deleteById(id);
    }

    @PutMapping("{id}")
    public void updateGroup(
            @PathVariable(value = "id") Integer id,
            @RequestPart("dto") CreateGroupDto group,
            @RequestPart(value = "file", required = false) MultipartFile file)
            throws IOException {
        groupService.updateGroup(id, group, file);
    }
}
