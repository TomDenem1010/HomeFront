package com.home.front.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontController {

  @GetMapping("/")
  public ModelAndView listEmployees() {
    Map<String, Object> model = new HashMap<>();
    model.put("greetings", "Welcome to the Home Portal!");

    return new ModelAndView("index", model);
  }
}
