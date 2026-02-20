package club.javafamily.demo06_assign_candidate;

import org.flowable.engine.*;
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
      final TaskService taskService = processEngine.getTaskService();

      final List<Task> list = taskService.createTaskQuery()
         .processDefinitionKey("holiday-candidate")
         .taskCandidateUser("李四") // 查询作为候选人的任务
         .list();

      for (Task task : list) {
         // holiday-candidate:1:4
         System.out.println(task.getProcessDefinitionId());
         // 创建请假单
         System.out.println(task.getName());
         // 普通员工
         System.out.println(task.getAssignee());
         // null
         System.out.println(task.getDescription());
         // 2508
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
      final TaskService taskService = processEngine.getTaskService();
      String currentUser = "李四";

      final Task task = taskService.createTaskQuery()
         .processDefinitionKey("holiday-candidate")
         .taskCandidateUser(currentUser) // 查询作为候选人的任务
         .singleResult();

      if(task != null) {
         // 拾取任务
         taskService.claim(task.getId(), currentUser);
      }
   }

   /**
    * 拾取任务
    * 一个任务被某个候选人拾取后, 其他候选人就无法拾取
    * 一个任务被某个候选人拾取后, 又不想处理了可以退还
    */
   @Test
   void testCancelClaimTask() {
      final TaskService taskService = processEngine.getTaskService();
      String currentUser = "张三";

      final Task task = taskService.createTaskQuery()
         .processDefinitionKey("holiday-candidate")
         .taskAssignee(currentUser) // 查询作为候选人的任务
         .singleResult();

      if(task != null) {
         // 归还任务
         taskService.unclaim(task.getId());
      }
   }

   /**
    * 拾取任务
    * 一个任务被某个候选人拾取后, 其他候选人就无法拾取
    * 一个任务被某个候选人拾取后, 又不想处理了可以退还
    * 一个任务被某个候选人拾取后, 可以交接给其他候选人去执行
    */
   @Test
   void testTransferTask() {
      final TaskService taskService = processEngine.getTaskService();
      String currentUser = "李四";
      String transferUser = "王五";

      final Task task = taskService.createTaskQuery()
         .processDefinitionKey("holiday-candidate")
         .taskAssignee(currentUser) // 查询作为候选人的任务
         .singleResult();

      if(task != null) {
         // 交接任务
         taskService.setAssignee(task.getId(), transferUser);
      }
   }

   @Test
   void testCompleteTask() {
      final TaskService taskService = processEngine.getTaskService();
      String currentUser = "王五";

      final Task task = taskService.createTaskQuery()
         .processDefinitionKey("holiday-candidate")
         .taskAssignee(currentUser) // 查询作为候选人的任务
         .singleResult();

      // 完成任务
      taskService.complete(task.getId());
   }

}
