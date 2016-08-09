package com.flyfish.demo.gson;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年8月9日 上午9:58:04 
 *</p>
  * @version 1.0  
*/
public class TestGson {
  public static void main(String[] args) {
    Gson gson=new Gson();
    String json="{\"id\":\"2\",\"name\":\"json技术\"}";
    Book b=gson.fromJson(json,Book.class);
    System.out.println("id:"+b.getId()+";书名:"+b.getName());
    
    String listJson= "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";;
    List list=gson.fromJson(listJson, new TypeToken<List>() {}.getType());
    System.out.println(list);
  }
}
