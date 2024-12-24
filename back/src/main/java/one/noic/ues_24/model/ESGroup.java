package one.noic.ues_24.model;

import lombok.NoArgsConstructor;
import one.noic.ues_24.controller.dto.group.CreateGroupDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "group_index")
@NoArgsConstructor
public class ESGroup {
    
    public ESGroup(CreateGroupDto dto) {
        this.name = dto.name;
        this.description = dto.description;
        this.rules = dto.rules;
        this.averageLikes = 0D;
        this.numberOfPosts = 0L;
    }

    @Id
    @Field(name = "id", type = FieldType.Integer)
    public Integer id;
    @Field(name = "name", type = FieldType.Text, normalizer = "lowerasciinormalizer")
    public String name;
    @Field(name = "description", type = FieldType.Text, normalizer = "lowerasciinormalizer")
    public String description;
    @Field(name = "fileContent", type = FieldType.Text, normalizer = "lowerasciinormalizer")
    public String fileContent;
    @Field(name = "numberOfPosts", type = FieldType.Long)
    public Long numberOfPosts;
    @Field(name = "rules", type = FieldType.Text, normalizer = "lowerasciinormalizer")
    public String rules;
    @Field(name = "averageLikes", type = FieldType.Double)
    public Double averageLikes;
}
