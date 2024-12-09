package one.noic.ues_24.controller;

import one.noic.ues_24.controller.dto.group.CreateGroupDto;
import one.noic.ues_24.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public void crateGroup(@RequestBody CreateGroupDto group) {
        groupService.createGroup(group);
    }

}
