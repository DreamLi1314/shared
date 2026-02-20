package club.javafamily.demo08_assign_candidate_group;

import org.flowable.engine.*;
import org.flowable.engine.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test01Repository {


   ProcessEngine processEngine;

   @BeforeEach
   public void init() {
      processEngine = ProcessEngines.getDefaultProcessEngine();
   }

   @Test
   void testDeploy() {
      final RepositoryService repositoryService
         = processEngine.getRepositoryService();

      final Deployment deployment = repositoryService.createDeployment()
         .addClasspathResource("请假流程-候选人组.bpmn20.xml")
         .name("请假流程")
         .deploy();

      System.out.println(deployment.getId()); // 17501
      System.out.println(deployment.getName()); // 请假流程
   }

   /**
    * 查询流程定义信息
    */
   @Test
   void testDeployQuery() {
      final RepositoryService repositoryService
         = processEngine.getRepositoryService();

      final ProcessDefinitionQuery processDefinitionQuery
         = repositoryService.createProcessDefinitionQuery();

      final ProcessDefinition processDefinition
         = processDefinitionQuery.deploymentId("17501")
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
      final RepositoryService repositoryService
         = processEngine.getRepositoryService();

      // 删除部署的流程
      // cascade 为 false: 如果部署的流程启动了就不允许删除
      repositoryService.deleteDeployment("7501", true);
   }
}
