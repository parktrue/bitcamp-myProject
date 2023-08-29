package bitcamp.util;

import java.io.InputStream;
import java.util.UUID;
import javax.servlet.http.Part;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class NcpObjectStorageService {
  final AmazonS3 s3;

  public NcpObjectStorageService(NcpConfig ncpConfig) {
    s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
            ncpConfig.getEndPoint(), ncpConfig.getRegionName()))
        .withCredentials(new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(ncpConfig.getAccessKey(), ncpConfig.getSecretKey())))
        .build();
  }

  public String uploadFile(String bucketName, String dirPath, Part part) {
    if (part.getSize() == 0) {
      return null;
    }

    try (InputStream fileIn = part.getInputStream()) {
      String filename = UUID.randomUUID().toString();

      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentType(part.getContentType());

      PutObjectRequest objectRequest =
          new PutObjectRequest(bucketName, dirPath + filename, fileIn, objectMetadata)
              .withCannedAcl(CannedAccessControlList.PublicRead);

      s3.putObject(objectRequest);

      // return s3.getUrl(bucketName, dirPath + filename).toString();
      return filename;

    } catch (Exception e) {
      throw new RuntimeException("파일 업로드 오류", e);
    }
  }
}
