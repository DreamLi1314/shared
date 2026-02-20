package club.javafamily.demo09_exclusive_gateway;

import org.flowable.engine.*;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test03Task {

   ProcessEngine processEngine;

   @BeforeEach
   public void init() {
      processEngine = ProcessEngines.getDefaultProcessEngine();
   }

   @Test
   void testQueryTask() {
      String currentUserId = "zhangsan";
      final TaskService taskService = processEngine.getTaskService();

      final List<Task> list = taskService.createTaskQuery()
         .processDefinitionKey("holiday-exclusive-gateway")
         .taskAssignee(currentUserId)
         .list();

      for (Task task : list) {
         // holiday-candidate-group:1:17504
         System.out.println(task.getProcessDefinitionId());
         // 创建请假单
         System.out.println(task.getName());
         // 普通员工
         System.out.println(task.getAssignee());
         // null
         System.out.println(task.getDescription());
         // 20006
         System.out.println(task.getId());

         System.out.println("---------------------");
      }
   }

   @Test
   void testCompleteTask() {
      String currentUserId = "zhangsan";
      final TaskService taskService = processEngine.getTaskService();

      final Task task = taskService.createTaskQuery()
         .processDefinitionKey("holiday-exclusive-gateway")
         .taskAssignee(currentUserId)
         .singleResult();

      if(task != null) {
         // 完成任务
         taskService.complete(task.getId());
         System.out.println("完成任务成功");
      }
   }

   @Test
   void testSetVars() {
      final RuntimeService runtimeService = processEngine.getRuntimeService();

      Map<String, Object> vars = new HashMap<>();
      vars.put("num", 4);

      runtimeService.setVariables("12501", vars);
   }

}
