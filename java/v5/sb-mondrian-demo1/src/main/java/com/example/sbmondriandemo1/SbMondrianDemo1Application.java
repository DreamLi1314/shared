package com.example.sbmondriandemo1;

import mondrian.olap.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintWriter;

@SpringBootApplication
public class SbMondrianDemo1Application {

   public static void main(String[] args) {
      String connectString = "Provider=mondrian;"
         + "Jdbc=jdbc:postgresql://106.75.101.61:5432/db_test;"
         + "JdbcDrivers=org.postgresql.Driver;"
         + "JdbcUser=root;"
         + "JdbcPassword=yiJi0713;"
         + "Catalog=file:/Users/dreamli/Workspace/shared/java/v5/sb-mondrian-demo1/src/main/resources/schemas/employee.xml;"
         ;

      System.out.println(connectString);

      Connection connection = null;

      connection = DriverManager.getConnection(connectString, null);
      Query query = connection.parseQuery(
         "select {[Measures].[Salary]} ON COLUMNS, " +
            "{[Employee].[employeeId].Members} ON ROWS " +
            "from CubeTest " +
            "where [Time].[year].[2022]"
      );

      Result result = connection.execute(query);
      result.print(new PrintWriter(System.out,true));

      SpringApplication.run(SbMondrianDemo1Application.class, args);
   }

}
