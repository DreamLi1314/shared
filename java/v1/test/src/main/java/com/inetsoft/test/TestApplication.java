package com.inetsoft.test;

import com.inetsoft.test.bean.TestProp;
import com.inetsoft.test.controller.SVGController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class TestApplication {

   public static void main(String[] args) throws Exception {
      SpringApplication.run(TestApplication.class, args);
   }

}
