package club.javafamily.service;

import club.javafamily.grpc.lib.*;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author Jack Li
 * @date 2022/1/18 11:27 上午
 * @description
 */
@Service
public class GrpcClientService {

   @GrpcClient("grpc-server")
   private SayHelloGrpc.SayHelloBlockingStub sayHelloStub;

   public String sendMessage(final String name) {
      try {
         final HelloReply response = this.sayHelloStub.sayHello(
            HelloRequest.newBuilder().setName(name).build());
         return response.getMessage();
      } catch (final StatusRuntimeException e) {
         return "FAILED with " + e.getStatus().getCode().name();
      }
   }

}
