package club.javafamily.demo01_guide;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.*;
import org.junit.jupiter.api.*;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test01Repository {

   ProcessEngineConfiguration configuration;

   @BeforeEach
   public void init() {
      configuration = new StandaloneProcessEngineConfiguration();

      // 配置数据库
      configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
      configuration.setJdbcUsername("root");
      configuration.setJdbcPassword("Yiji0713!");
      configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/db_flowable1?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true");

      configuration.setDatabaseSchemaUpdate(
         ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
   }

   @Test
   void testDeploy() {
      final ProcessEngine processEngine = configuration.buildProcessEngine();

      final RepositoryService repositoryService
         = processEngine.getRepositoryService();

      final Deployment deployment = repositoryService.createDeployment()
         .addClasspathResource("holiday-request.bpmn20.xml")
         .name("请假流程")
         .deploy();

      System.out.println(deployment.getId()); // 7501
      System.out.println(deployment.getName()); // 请假流程
   }

   /**
    * 查询流程定义信息
    */
   @Test
   void testDeployQuery() {
      final ProcessEngine processEngine = configuration.buildProcessEngine();

      final RepositoryService repositoryService
         = processEngine.getRepositoryService();

      final ProcessDefinitionQuery processDefinitionQuery
         = repositoryService.createProcessDefinitionQuery();

      final ProcessDefinition processDefinition
         = processDefinitionQuery.deploymentId("1")
         .singleResult();

      System.out.println(processDefinition.getName()); // 请假流程0
      System.out.println(processDefinition.getResourceName()); // holiday-request.bpmn20.xml
      System.out.println(processDefinition.getDiagramResourceName()); // null
      System.out.println(processDefinition.getId()); // holidayRequest:1:3
      System.out.println(processDefinition.getDeploymentId()); // 1
   }

   /**
    * 删除流程定义信息
    */
   @Test
   void testDeployDelete() {
      final ProcessEngine processEngine = configuration.buildProcessEngine();

      final RepositoryService repositoryService
         = processEngine.getRepositoryService();

      // 删除部署的流程
      // cascade 为 false: 如果部署的流程启动了就不允许删除
      repositoryService.deleteDeployment("7501", true);
   }
}
