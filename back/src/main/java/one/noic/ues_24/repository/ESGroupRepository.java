package one.noic.ues_24.repository;

import one.noic.ues_24.model.ESGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

interface ESGroupRepository extends ElasticsearchRepository<ESGroup, Integer> {
}
