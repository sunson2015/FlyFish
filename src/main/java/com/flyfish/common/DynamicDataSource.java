package com.flyfish.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * description: 
 *<p>
 * @author 
 *<p>
 * create date：2016年4月27日 下午6:20:33 
 *</p>
  * @version 1.0  
*/
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSourceKey=new InheritableThreadLocal<String>();
    public static void setDataSourceKey(String dataSource){
        dataSourceKey.set(dataSource);
    }
    @Override
    protected Object determineCurrentLookupKey() {
        // TODO Auto-generated method stub
        return dataSourceKey.get();
    }

}
