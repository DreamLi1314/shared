package com.inetsoft.druid.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatasourceTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDatasource() throws Exception {
        Assert.assertNotNull("Datasource can't none.", dataSource);

        System.out.println("dataSource: " + dataSource);

        Connection connection = dataSource.getConnection();

        Assert.assertNotNull("Connection can't none.", connection);

        System.out.println("connection: " + connection);
        connection.close();
    }
}
