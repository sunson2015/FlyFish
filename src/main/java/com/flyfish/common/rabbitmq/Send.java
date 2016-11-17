package com.flyfish.common.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年11月17日 下午4:39:29 
 *</p>
  * @version 1.0  
*/
public class Send {
 public static void main(String[] args) throws IOException, TimeoutException {
    String queueName="hello";
    Connection con=new ConnectionFactory().newConnection();
    Channel channel=con.createChannel();
    channel.queueDeclare(queueName, false, false, false, null);
    channel.basicPublish("", queueName, null,"Hello World 我学rabitmq".getBytes());
}
}
