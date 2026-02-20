package club.javafamily.service;

import club.javafamily.grpc.lib.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author Jack Li
 * @date 2022/1/18 11:27 上午
 * @description
 */
@GrpcService
public class GrpcServerService extends SayHelloGrpc.SayHelloImplBase {

   @Override
   public void sayHello(HelloRequest req,
                        StreamObserver<HelloReply> responseObserver)
   {
      HelloReply reply = HelloReply.
         newBuilder()
         .setMessage("Hello ==> " + req.getName())
         .build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
   }

}
