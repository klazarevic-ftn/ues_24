package one.noic.ues_24.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.Transliterator;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("search")
public class SearchController {

    private final Transliterator cyrillicToLatin = Transliterator.getInstance("Cyrillic-Latin");
    private final ElasticsearchClient elasticsearchClient;

    public SearchController(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @PostMapping("group")
    public List<Map<String, Object>> searchGroup(@RequestPart("query") String queryJson) throws IOException {
        String transliterated = cyrillicToLatin.transliterate(queryJson);
        SearchResponse<JsonData> searchResponse = elasticsearchClient.search(builder ->
                builder.index("group_index")
                        .withJson(new StringReader(transliterated))
                        .highlight(h -> h
                                .preTags("<strong>")
                                .postTags("</strong>")
                                .fields("name", f -> f
                                        .requireFieldMatch(false))
                                .fields("description", f -> f
                                        .requireFieldMatch(false))
                                .fragmentSize(50)
                        ), JsonData.class);

        return searchResponse.hits().hits().stream().map(hit -> {
            Map<String, Object> result = new HashMap<>(hit.source().to(Map.class));
            result.put("highlights", hit.highlight());
            return result;
        }).toList();
    }

    @PostMapping("post")
    public List<Map<String, Object>> search(@RequestPart("query") String queryJson) throws IOException {
        String transliterated = cyrillicToLatin.transliterate(queryJson);
        SearchResponse<JsonData> searchResponse = elasticsearchClient.search(builder ->
                builder.index("post_index")
                        .withJson(new StringReader(transliterated))
                        .highlight(h -> h
                                .preTags("<strong>")
                                .postTags("</strong>")
                                .fields("title", f -> f
                                        .requireFieldMatch(false))
                                .fields("fullContent", f -> f
                                        .requireFieldMatch(false))
                                .fields("commentContent", f -> f
                                        .requireFieldMatch(false))
                                .fragmentSize(50)
                        ), JsonData.class);

        return searchResponse.hits().hits().stream().map(hit -> {
            Map<String, Object> result = new HashMap<>(hit.source().to(Map.class));
            result.put("highlights", hit.highlight());
            return result;
        }).toList();
    }

}
