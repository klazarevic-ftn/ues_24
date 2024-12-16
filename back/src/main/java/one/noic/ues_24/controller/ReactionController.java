package one.noic.ues_24.controller;


import one.noic.ues_24.controller.dto.reaction.ReactionDto;
import one.noic.ues_24.service.ReactionService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("reaction")
public class ReactionController {

    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping("")
    public void createReaction(@RequestPart("reaction") ReactionDto dto) {
        this.reactionService.createReaction(dto);
    }
}
