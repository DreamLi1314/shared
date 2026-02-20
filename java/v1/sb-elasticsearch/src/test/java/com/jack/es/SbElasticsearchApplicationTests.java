package com.jack.es;

import com.jack.es.pojo.Article;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbElasticsearchApplicationTests {

    @Autowired
    private JestClient jestClient;

    @Test
    public void contextLoads() {
    }

    /**
     * 新建索引.
     */
    @Test
    public void addIndex() {
        Article article = new Article(1, "Title", "Author1", "Content 哈1哈...");

        Index index = new Index.Builder(article).index("db_jack").type("t_news").build();

        try {
            DocumentResult result = jestClient.execute(index);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试检索.
     */
    @Test
    public void search() {
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match_phrase\" : {\n" +
                "            \"content\" : \"哈\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"highlight\": {\n" +
                "        \"fields\" : {\n" +
                "            \"content\" : {}\n" +
                "        }\n" +
                "    }\n" +
                "}";

        Search search = new Search.Builder(json).addIndex("db_jack").addType("t_news").build();

        try {
            SearchResult result = jestClient.execute(search);
            System.out.println("=====result===" + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
