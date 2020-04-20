package com.inetsoft.test;

import com.box.sdk.BoxAPIConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {

   public static void main(String[] args) {
      BoxAPIConnection api = new BoxAPIConnection("qnxrnhzdo48ibbi5sk9020e798m9rvf7",
         "5pt8EDfZ8TgGNjDc9Q0BEaGIPy5CPfzX", "hwi60YUrTyYOQCgvubiJcKlNHYYlgvf8");

      System.out.println("======api=====" + api);

      api.getAccessToken();
      SpringApplication.run(TestApplication.class, args);
   }

}
