package bitcamp.util;

public class NcpConfig {
  private String endPoint = "https://kr.object.ncloudstorage.com";
  private String regionName = "kr-standard";
  private String accessKey = System.getProperty("accessKey");
  private String secretKey = System.getProperty("secretKey");

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }


}
