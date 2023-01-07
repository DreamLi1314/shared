package club.javafamily.demo08_assign_candidate_group;

import org.flowable.engine.*;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
      final IdentityService identityService = processEngine.getIdentityService();
      // 查询当前用户所在的组
      final Group group = identityService.createGroupQuery()
         .groupMember(currentUserId)
         .singleResult();

      final List<Task> list = taskService.createTaskQuery()
         .processDefinitionKey("holiday-candidate-group")
         .taskCandidateGroup(group.getId()) // 查询候选组的任务
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

   /**
    * 拾取任务
    * 一个任务被某个候选人拾取后, 其他候选人就无法拾取
    */
   @Test
   void testClaimTask() {
      String currentUserId = "zhangsan";
      final TaskService taskService = processEngine.getTaskService();
      final IdentityService identityService = processEngine.getIdentityService();
      // 查询当前用户所在的组
      final Group group = identityService.createGroupQuery()
         .groupMember(currentUserId)
         .singleResult();

      final Task task = taskService.createTaskQuery()
         .processDefinitionKey("holiday-candidate-group")
         .taskCandidateGroup(group.getId()) // 查询候选组的任务
         .singleResult();

      if(task != null) {
         // 拾取任务
         taskService.claim(task.getId(), currentUserId);
         System.out.println("拾取任务成功");
      }
   }

   @Test
   void testCompleteTask() {
      String currentUserId = "zhangsan";
      final TaskService taskService = processEngine.getTaskService();

      final Task task = taskService.createTaskQuery()
         .processDefinitionKey("holiday-candidate-group")
         .taskAssignee(currentUserId) // 查询候选组的任务
         .singleResult();

      if(task != null) {
         // 完成任务
         taskService.complete(task.getId());
         System.out.println("完成任务成功");
      }
   }

}
