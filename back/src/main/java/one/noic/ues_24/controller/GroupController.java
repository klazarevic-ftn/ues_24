package one.noic.ues_24.controller;

import one.noic.ues_24.controller.dto.group.CreateGroupDto;
import one.noic.ues_24.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("")
    public ResponseEntity<String> getGroupById(){
        return new ResponseEntity<>("Herro", HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("")
    public void crateGroup(@RequestPart("group") CreateGroupDto group, @RequestPart("file") MultipartFile file) throws IOException {
        groupService.createGroup(group, file);
    }

}
