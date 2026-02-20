package club.javafamily.conf;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jack Li
 * @date 2022/1/18 11:26 上午
 * @description
 */
public class LogGrpcInterceptor implements ServerInterceptor {

   private static final Logger log = LoggerFactory.getLogger(LogGrpcInterceptor.class);

   @Override
   public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> serverCall,
      Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler)
   {
      log.info(serverCall.getMethodDescriptor().getFullMethodName());
      return serverCallHandler.startCall(serverCall, metadata);
   }

}
