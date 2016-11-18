package com.flyfish.common.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年11月17日 下午4:45:16 
 *</p>
  * @version 1.0  
*/
public class Receive {
 public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
     String queueName="hello";
     Connection con=RabbitMqConncetion.getConnection();
     Channel channel=con.createChannel();
     channel.queueDeclare(queueName, false, false, false, null);
     QueueingConsumer consumer=new QueueingConsumer(channel);
     channel.basicConsume(queueName, true,consumer);
    
     while(true){
         QueueingConsumer.Delivery deliver=consumer.nextDelivery();
         System.out.println(new String(deliver.getBody()));
        
     }
     
     
}
}
