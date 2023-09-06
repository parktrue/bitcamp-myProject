package bitcamp.myapp.config;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "bitcamp.myapp.config",
    "bitcamp.myapp.service"}
)
public class RootConfig {

  {
    System.out.println("RootConfig() 호출됨!");
  }
}

