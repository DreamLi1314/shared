package club.javafamily.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @author Jack Li
 * @date 2022/10/12 下午6:28
 * @description
 */
public class ApproveCompletedListener implements TaskListener {
   @Override
   public void notify(DelegateTask delegateTask) {
      System.out.println("审批完成 触发监听器!!!");

      System.out.println("delegateTask.getEventName() = "
         + delegateTask.getEventName());

      System.out.println("delegateTask.getEventHandlerId() = " + delegateTask.getEventHandlerId());

      System.out.println("delegateTask.getId() = " + delegateTask.getId());
      System.out.println("delegateTask.getName() = " + delegateTask.getName());
      System.out.println("delegateTask.getAssignee() = " + delegateTask.getAssignee());
      System.out.println("delegateTask.getProcessDefinitionId() = " + delegateTask.getProcessDefinitionId());

      System.out.println("=============");
   }
}
