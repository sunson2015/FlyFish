package com.flyfish.common.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年11月18日 下午2:14:32 
 *</p>
  * @version 1.0  
*/
public class Producer {
  public enum XT{
      DEFAULT,DIRECT,TOPIC,HEADERS,FANOUT
  }
  private static final String QUEUE_NAME="log2";
  public static void main(String[] args) throws IOException, TimeoutException {
    Connection con=RabbitMqConncetion.getConnection();
    Channel channel=con.createChannel();
    XT xt=XT.FANOUT;
    switch(xt){
        case DEFAULT://默认指定队列发送消息，消息只会被一个consume处理，多个消费者会轮训处理，若无consume,则不会丢失
            channel.queueDeclare(QUEUE_NAME, true, false, true, null);
            while(GetInputString()){
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,  message.getBytes());
                System.out.println("Send" +message);
            }
            break;
        case FANOUT:
            channel.exchangeDeclare(XCHAG_NAME, "fanout",true,true,null);
            while(GetInputString()){
                channel.basicPublish(XCHAG_NAME, "", null, message.getBytes());
                System.out.println("Send" + message);
            }
            break;
        case DIRECT:
            channel.exchangeDeclare(XCHAG_NAME, "direct", true,true,null);
            while(GetInputString()){
                String [] temp=StringUtils.split(message," ");
                channel.basicPublish(XCHAG_NAME, temp[0],null, temp[1].getBytes());
                System.out.println("Send" +message);
                
            }
            break;
            
        case TOPIC:
            channel.exchangeDeclare(XCHAG_NAME, "topic", true, true, null);
            while(GetInputString()){
                String[] temp=StringUtils.split(message," ");
                channel.basicPublish(XCHAG_NAME, temp[0], null, temp[1].getBytes());
                System.out.println("Send "+message);
            }
            break;
        case HEADERS:
            channel.exchangeDeclare(XCHAG_NAME,"headers",true,true,null);
            while(GetInputString()){
                String [] temp=StringUtils.split(message, " ");
                Map<String,Object> headers=new HashMap<String,Object>();
                headers.put("name", temp[0]);
                headers.put("sex", temp[1]);
                AMQP.BasicProperties.Builder builder=new AMQP.BasicProperties.Builder().headers(headers);
                System.out.println("Send" +message);
            }
            break;
    }
    channel.close();
    con.close();
  }
  
  private static Scanner scanner=new Scanner(System.in);
  private static String message="";
  public static String XCHAG_NAME="xchg3";
  private static boolean GetInputString(){
      message=scanner.nextLine();
      if(message.length()==0){
          return false;
      }
      return true;
  }
}
