package one.noic.ues_24.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl("b9075ae467dc9d25b7258ec0068dbce5c7e240c9811c34d946ac4707367db2c3")
                .withBasicAuth("elastic","elastic")
                .build();
    }
}
