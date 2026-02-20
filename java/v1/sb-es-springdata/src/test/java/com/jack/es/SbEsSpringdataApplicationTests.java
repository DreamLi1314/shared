package com.jack.es;

import com.jack.es.po.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbEsSpringdataApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testSearch() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchPhraseQuery("name", "Boo"))
                .build();

        List<Book> books = elasticsearchTemplate.queryForList(searchQuery, Book.class);

        System.out.println("====" + books);
    }

}
