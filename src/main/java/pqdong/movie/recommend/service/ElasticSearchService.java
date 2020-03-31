package pqdong.movie.recommend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.entity.CommentEs;

import javax.annotation.Resource;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * ElasticSearchService
 *
 * @author pqdong
 * @since 2020/03/31
 */

@Service
@Slf4j
public class ElasticSearchService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    public List<CommentEs> getAllIndex(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withPageable(PageRequest.of(0, 100));
        SearchQuery query = builder.build();
        List<CommentEs> index = elasticsearchTemplate.queryForList(query, CommentEs.class);
        log.info("{}", index);
        return index;
    }
}
