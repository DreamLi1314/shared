package club.javafamily.jdbc;

import java.sql.*;

/**
 * @author Jack Li
 * @date 2023/10/25 下午5:59
 * @description
 */
public class Demo01 {

   public static void main(String[] args) {
      // JDBC连接URL，需要根据你的Hive配置进行修改
      String jdbcURL = "jdbc:hive2://10.1.109.140:10000/default";

      // Hive用户名和密码（可选）
      String user = "yourUsername";
      String password = "yourPassword";

      try {
         // 加载Hive JDBC驱动程序
         Class.forName("org.apache.hive.jdbc.HiveDriver");

         // 创建连接
         Connection connection = DriverManager.getConnection(jdbcURL);

         // 创建Statement
         Statement statement = connection.createStatement();

         // 执行Hive查询
         String query = "SELECT * FROM t_test";
         ResultSet resultSet = statement.executeQuery(query);

         // 处理查询结果
         while (resultSet.next()) {
            // 处理每一行数据
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            System.out.println(id + ", " + name);
         }

         // 关闭连接
         resultSet.close();
         statement.close();
         connection.close();
      }
      catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
      }
   }

}
