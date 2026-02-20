package club.javafamily.sb_base_demo1.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//   @GetMapping("/test")
//   public void contextLoads() throws Exception {
//      // Create a minioClient with the MinIO server playground, its access key and secret key.
//      MinioClient minioClient =
//         new MinioClient("http://minio.aws.mlogcn.com", "admin", "yiJi123456");
//
//      // Make 'docs' bucket if not exist.
//      boolean found =
//         minioClient.bucketExists("docs");
//      if (!found) {
//         // Make a new bucket called 'asiatrip'.
//         minioClient.makeBucket("docs");
//      } else {
//         System.out.println("Bucket 'docs' already exists.");
//      }
//
//      // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
//      // 'asiatrip'.
//      String filePath = "/tianqi.docx";
//
//      System.out.println("exists: " + new File(filePath).exists());
//
//      minioClient.putObject("docs", "tianqi.docx",
//         "/tianqi.docx");
//      System.out.println(
//         "'一带一路城市(1).xlsx' is successfully uploaded as "
//            + "object '一带一路城市(1).xlsx' to bucket 'docs'.");
//   }

}
