package com.flyfish.demo.zookeeper;


import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年11月29日 上午10:31:02 
 *</p>
  * @version 1.0  
*/
public class TestZookeeper {
 public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
     
     ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181", 3000, null);
     System.out.println("=========创建节点===========");
     if(zookeeper.exists("/test", false) == null)
     {
         zookeeper.create("/test", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
     }
     System.out.println("=============查看节点是否安装成功===============");
     System.out.println(new String(zookeeper.getData("/test", false, null)));
     System.out.println("=================修改节点的数据==================");
     String data="zNode2";
     zookeeper.setData("/test", data.getBytes(), -1);     
     System.out.println("========查看修改的节点是否成功=========");
     System.out.println(new String(zookeeper.getData("/test", false, null)));
     System.out.println("=======删除节点==========");
     zookeeper.delete("/test", -1);
     
     System.out.println("==========查看节点是否被删除============");
     System.out.println("节点状态：" + zookeeper.exists("/test", false));
     
     zookeeper.close();
  }
 
}
