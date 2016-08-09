package com.flyfish.demo.gson;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年8月9日 上午9:58:20 
 *</p>
  * @version 1.0  
*/
public class Book {
  private String id;
  private String name;
  public Book(){
      super();
  }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
  
}
