package com.flyfish.demo.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年5月6日 下午4:33:20 
 *</p>
  * @version 1.0  
*/
public class TestCollection {
  //Collections是javaUtil下的类，它包含所有各种集合操作的静态方法。
  //Collection是java.util下的接口，它是各种集合结构的父接口。
    public static void main(String[] args) {
        List<Integer> list1=new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(3);
        
        List<Integer> list2=new ArrayList<Integer>();
        list2.add(4);
        list2.add(5);
        list2.add(666);
        Collections.reverse(list2);
        //Collections.copy(list1, list2);
        Collections.addAll(list1,88,99);
        for(Integer i:list1){
            System.out.println("list1:"+i);
        }
        for(Integer j: list2){
            System.out.println("list2:"+j);
        }
        Collections.sort(list1,new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                return o1<o2? -1: 1;
            }
            
        });
        System.out.println("list1 size:"+list1.size());
        System.out.println("list2 size:"+list2.size());
        LogbackConfigListener l;
     
    }
}
