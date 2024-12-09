package one.noic.ues_24.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "group_index")
public class ESGroup {
    public String id;
    @Field(name = "name", type = FieldType.Text)
    public String name;
    @Field(name = "description", type = FieldType.Text)
    public String description;
    @Field(name = "fileContent", type = FieldType.Text)
    public String fileContent;
    @Field(name = "numberOfPosts", type = FieldType.Long)
    public Long numberOfPosts;
    @Field(name = "rules", type = FieldType.Text)
    public String rules;
    @Field(name = "averageLikes", type = FieldType.Double)
    public Double averageLikes;
}
