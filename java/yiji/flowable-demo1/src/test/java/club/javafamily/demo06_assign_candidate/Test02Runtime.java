package club.javafamily.demo06_assign_candidate;

import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
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

      Map<String, Object> vars = new HashMap<>();

      vars.put("candidate1", "张三");
      vars.put("candidate2", "李四");
      vars.put("candidate3", "王五");

      final ProcessInstance processInstance
         = runtimeService.startProcessInstanceByKey("holiday-candidate", vars);

      // holiday-candidate:1:4
      System.out.println(processInstance.getProcessDefinitionId());

      // null
      System.out.println(processInstance.getActivityId());

      // 2501
      System.out.println(processInstance.getId());

   }
}
