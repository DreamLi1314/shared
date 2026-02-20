package club.javafamily.sb_base_demo1;

import io.minio.*;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;
import sun.misc.IOUtils;

public class ReadMinIOTests {

   @Test
   void contextLoads() throws Exception {
      try {
         // Create a minioClient with the MinIO server playground, its access key and secret key.
         MinioClient minioClient =
            MinioClient.builder()
//               .endpoint("http://minio.aws.mlogcn.com")
               .endpoint("http://192.168.1.10:9000")
               .credentials("admin", "yiJi123456")
               .build();

         final GetObjectArgs getObjectArgs = GetObjectArgs.builder()
            .bucket("docs")
            .object("-645118676txt.txt")
            .build();


         final GetObjectResponse in = minioClient.getObject(getObjectArgs);

         final byte[] bytes = IOUtils.readAllBytes(in);

      } catch (MinioException e) {
         System.out.println("Error occurred: " + e);
         System.out.println("HTTP trace: " + e.httpTrace());
      }

   }

}
