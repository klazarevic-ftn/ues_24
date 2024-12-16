package one.noic.ues_24.service;

import one.noic.ues_24.controller.dto.reaction.ReactionDto;
import one.noic.ues_24.model.ESGroup;
import one.noic.ues_24.model.Reaction;
import one.noic.ues_24.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final GroupService groupService;
    @Autowired
    public ReactionService(ReactionRepository reactionRepository, ElasticsearchOperations elasticsearchOperations, GroupService groupService) {
        this.reactionRepository = reactionRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.groupService = groupService;
    }

    public void createReaction(ReactionDto dto) {
        Reaction reaction = new Reaction(dto);
        this.reactionRepository.save(reaction);

//        ESGroup esGroup = this.elasticsearchOperations.get(dto.group_id + "", ESGroup.class);
//        esGroup.numberOfPosts = this.groupService.getNumberOfPosts(dto.group_id);
//        esGroup.averageLikes = this.groupService.getAverageReact(dto.group_id);
//        this.elasticsearchOperations.save(esGroup);
    }
}
