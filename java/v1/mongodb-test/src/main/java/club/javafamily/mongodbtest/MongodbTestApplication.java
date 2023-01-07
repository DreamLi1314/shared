package club.javafamily.mongodbtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.TimeZone;

@EnableMongoAuditing
@SpringBootApplication
public class MongodbTestApplication {

    public static void main(String[] args) {
        // setting default time zone
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));

        SpringApplication.run(MongodbTestApplication.class, args);
    }

}
