package com.flyfish.common.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年11月18日 下午3:06:56 
 *</p>
  * @version 1.0  
*/
public class Consumer {
  private static final String QUEUE_NAME="log2";
  public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
    Connection con=RabbitMqConncetion.getConnection();
    Channel channel =con.createChannel();
    String queueName=QUEUE_NAME;
    Producer.XT xt=Producer.XT.TOPIC;
    switch(xt){
        case DEFAULT:
            channel.queueDeclare(queueName, true, false, true, null);
            break;
        case FANOUT:
            channel.exchangeDeclare(Producer.XCHAG_NAME, "fanout" , true, true,null);
            queueName=channel.queueDeclare().getQueue();
            channel.queueBind(queueName, Producer.XCHAG_NAME, "");
            break;
        case DIRECT:
            channel.exchangeDeclare(Producer.XCHAG_NAME, "direct", true, true, null);
            queueName=channel.queueDeclare().getQueue();
            channel.queueBind(queueName, Producer.XCHAG_NAME, "info");
            channel.queueBind(queueName, Producer.XCHAG_NAME, "warning");
            break;
        case TOPIC:
            channel.exchangeDeclare(Producer.XCHAG_NAME, "topic", true,true,null);
            queueName=channel.queueDeclare().getQueue();
            channel.queueBind(queueName, Producer.XCHAG_NAME, "warning.#");
            channel.queueBind(queueName, Producer.XCHAG_NAME, "*.blue");
            break;
        case HEADERS:
            channel.exchangeDeclare(Producer.XCHAG_NAME, "headers", true, true, null);
            queueName=channel.queueDeclare().getQueue();
            Map<String,Object> headers=new HashMap<String,Object>();
            headers.put("name","test");
            headers.put("sex", "male");
            headers.put("x-match", "any");
            channel.queueBind(queueName, Producer.XCHAG_NAME, "",headers);
            break;
   }
    channel.basicQos(1);
    QueueingConsumer consumer=new QueueingConsumer(channel);
    channel.basicConsume(queueName, false,consumer);
    while(true){
        QueueingConsumer.Delivery delivery=consumer.nextDelivery();
        System.out.println("Received "+ new String(delivery.getBody()));
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }
}
}
