package club.javafamily.sbliteflowdemo1.controller;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jack Li
 * @date 2023/6/12 下午10:40
 * @description
 */
@RestController
public class LiteFlowTestController {

   @Resource
   private FlowExecutor flowExecutor;

   @GetMapping("/litefllow/test01")
   public String testConfig(
      @RequestParam("chain") String chain,
      @RequestParam("arg") String arg)
   {
      LiteflowResponse response = flowExecutor.execute2Resp(chain, arg);

      System.out.println(response);

      return response.getExecuteStepStr();
   }

}
