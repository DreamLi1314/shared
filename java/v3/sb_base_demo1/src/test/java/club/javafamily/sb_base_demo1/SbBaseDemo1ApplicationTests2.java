package club.javafamily.sb_base_demo1;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbBaseDemo1ApplicationTests2 {

//   @Test
//   void contextLoads() throws Exception {
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
//
//      String path = "/Users/dreamli/Workspace/yiji/dingtalk/tianqi.docx";
//      final File file = new File(path);
//      final FileInputStream in = new FileInputStream(file);
//
//      Path filePath = Paths.get(path);
//      String contentType = Files.probeContentType(filePath);
//      long size = Files.size(filePath);
//
//      minioClient.putObject("docs", "tianqi.docx",
//         in, size, contentType);
//      System.out.println(
//         "'一带一路城市(1).xlsx' is successfully uploaded as "
//            + "object '一带一路城市(1).xlsx' to bucket 'docs'.");
//   }

}
