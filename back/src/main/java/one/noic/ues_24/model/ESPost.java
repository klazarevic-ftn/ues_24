package one.noic.ues_24.model;

import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import one.noic.ues_24.controller.dto.post.PostDto;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "post_index")
@NoArgsConstructor
public class ESPost {

    public ESPost(PostDto dto) {
        this.title = dto.title;
        this.fullContent = dto.content;

        this.numberOfLikes = 0L;
        this.commentContent = "";
    }

    @Id
    @Field(name = "id", type = FieldType.Integer)
    public int id;
    @Field(name = "title", type = FieldType.Text, normalizer = "lowerasciinormalizer")
    public String title;
    @Field(name = "fullContent", type = FieldType.Text, normalizer = "lowerasciinormalizer")
    public String fullContent;
    @Field(name = "fileContent", type = FieldType.Text, normalizer = "lowerasciinormalizer")
    public String fileContent;
    @Field(name = "numberOfLikes", type = FieldType.Long)
    public Long numberOfLikes;
    @Field(name = "numberOfComments", type = FieldType.Long)
    public Integer numberOfComments;
    @Field(name = "commentContent", type = FieldType.Text, normalizer = "lowerasciinormalizer")
    public String commentContent;
}
