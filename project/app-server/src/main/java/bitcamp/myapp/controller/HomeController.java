package bitcamp.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  {
    System.out.println("HomeController 생성!");
  }

  @GetMapping("/")
  public String home() throws Exception {
    return "index";
  }
}
