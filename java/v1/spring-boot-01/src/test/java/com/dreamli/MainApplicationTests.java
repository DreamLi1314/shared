package com.dreamli;

import com.dreamli.po.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ConditionalOnBean 只有 IOC 中存在 Person 这个 Bean 时才实例化 MainApplicationTests
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ConditionalOnBean(Person.class)
public class MainApplicationTests {

    @Autowired
    private Person person;



    @Test
    public void contextLoads() {
        System.out.println(person);

        LOGGER.trace("This is trace.....");
        LOGGER.debug("This is debug.....");
        LOGGER.info("This is info.....");
        LOGGER.warn("This is warn.....");
        LOGGER.error("This is error.....");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplicationTests.class);
}
