package org.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author Jack Li
 * @date 2022/10/11 下午11:29
 * @description Flowable 触发器
 */
public class SendRejectionMail implements JavaDelegate {

   /**
    * 触发执行的逻辑
    * @param execution
    */
   @Override
   public void execute(DelegateExecution execution) {

      System.out.println("模拟发送邮件: 拒绝请假申请!!!");

   }
}
