package club.javafamily.sbliteflowdemo1.components;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * @author Jack Li
 * @date 2023/6/12 下午10:37
 * @description
 */
@Component("c")
public class CCmp extends NodeComponent {
   @Override
   public void process() throws Exception {
      System.out.println("CCmp" + this.getRequestData());
   }
}