package one.noic.ues_24.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "post_index")
public class ESPost {
    private int id;
    @Field(name = "title", type = FieldType.Text)
    private String title;
    @Field(name = "fullContent", type = FieldType.Text)
    private String fullContent;
    @Field(name = "fileContent", type = FieldType.Text)
    private String fileContent;
    @Field(name = "numberOfLikes", type = FieldType.Long)
    private Long numberOfLikes;
    @Field(name = "commentContent", type = FieldType.Text)
    private String commentContent;
}
