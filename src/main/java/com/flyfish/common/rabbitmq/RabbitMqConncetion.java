package com.flyfish.common.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年11月18日 上午11:10:25 
 *</p>
  * @version 1.0  
*/
public class RabbitMqConncetion {
   private static Connection con=null;
  public static Connection getConnection() throws IOException, TimeoutException{
      if(con==null){
          ConnectionFactory fa=new ConnectionFactory();
          fa.setUsername("guest");
          fa.setPassword("guest");
          fa.setVirtualHost("/");
          fa.setHost("127.0.0.1");
          fa.setPort(5672);
          con=fa.newConnection();
          
      }
      return con;
  }
}
