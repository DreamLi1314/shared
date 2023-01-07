package club.javafamily.demo08_assign_candidate_group;

import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test02Runtime {

   ProcessEngine processEngine;

   @BeforeEach
   public void init() {
      processEngine = ProcessEngines.getDefaultProcessEngine();
   }

   @Test
   void testRunProcess() {
      final RuntimeService runtimeService = processEngine.getRuntimeService();

      final IdentityService identityService = processEngine.getIdentityService();
      final Group devGroup = identityService.createGroupQuery()
         .groupId("group1")
         .singleResult();

      Map<String, Object> vars = new HashMap<>();

      vars.put("g1", devGroup.getId());

      final ProcessInstance processInstance
         = runtimeService.startProcessInstanceByKey("holiday-candidate-group", vars);

      // holiday-candidate-group:1:17504
      System.out.println(processInstance.getProcessDefinitionId());

      // null
      System.out.println(processInstance.getActivityId());

      // 20001
      System.out.println(processInstance.getId());

   }
}
