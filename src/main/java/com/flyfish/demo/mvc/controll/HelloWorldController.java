package com.flyfish.demo.mvc.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * description: 
 *<p>
 * @author  hongpingliu@creditease.cn
 *<p>
 * create date：2016年4月27日 上午10:04:59 
 *</p>
  * @version 1.0  
*/
@Controller
public class HelloWorldController {
  @RequestMapping("/test/hellomvc")
  public String helloWorld(Model model){
      System.out.println("aa");
      model.addAttribute("message","Spring 4 mvc Hello world");
      return "hellomvc";
  }
}
