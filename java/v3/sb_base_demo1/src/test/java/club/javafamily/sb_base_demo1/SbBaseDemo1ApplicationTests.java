package club.javafamily.sb_base_demo1;

import io.minio.*;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbBaseDemo1ApplicationTests {

   @Test
   void contextLoads() throws Exception {
      try {
         // Create a minioClient with the MinIO server playground, its access key and secret key.
         MinioClient minioClient =
            MinioClient.builder()
               .endpoint("http://minio.aws.mlogcn.com")
               .credentials("admin", "yiJi123456")
               .build();

         // Make 'docs' bucket if not exist.
         boolean found =
            minioClient.bucketExists(BucketExistsArgs.builder().bucket("docs").build());
         if (!found) {
            // Make a new bucket called 'asiatrip'.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("docs").build());
         } else {
            System.out.println("Bucket 'docs' already exists.");
         }

         // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
         // 'asiatrip'.
         minioClient.uploadObject(
            UploadObjectArgs.builder()
               .bucket("docs")
               .object("一带一路城市(1).xlsx")
               .filename("/Users/dreamli/Workspace/yiji/dingtalk/一带一路城市(1).xlsx")
               .build());
         System.out.println(
            "'一带一路城市(1).xlsx' is successfully uploaded as "
               + "object '一带一路城市(1).xlsx' to bucket 'docs'.");
      } catch (MinioException e) {
         System.out.println("Error occurred: " + e);
         System.out.println("HTTP trace: " + e.httpTrace());
      }

   }

}
