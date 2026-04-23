
package com.college.cms.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class SampleController {
  @GetMapping("/")
  public String home(){ return "College Management API"; }
}
