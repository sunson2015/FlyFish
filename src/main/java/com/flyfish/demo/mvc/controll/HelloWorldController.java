package com.flyfish.demo.mvc.controll;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyfish.model.login.entity.UserEntity;
import com.flyfish.model.login.mapper.UserMapper;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年4月27日 上午10:04:59 
 *</p>
  * @version 1.0  
*/
@Controller
public class HelloWorldController {
  @Autowired
  private UserMapper userMapper;
  @RequestMapping("/test/hellomvc")
  public String helloWorld(Model model){
      System.out.println("aa");
      model.addAttribute("message","Spring 4 mvc Hello world");
      UserEntity u=new UserEntity();
      u.setTitle("beijingchuban");
      u.setAuthor("liuda");
      u.setSubmissionDate(new Date());
      this.userMapper.insert(u);
      System.out.println(u.toString());
      return "hellomvc";
  }
}
