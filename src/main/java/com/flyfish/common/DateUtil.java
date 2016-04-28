package com.flyfish.common;

import org.mybatis.spring.SqlSessionFactoryBean;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;



/**
 * description: 
 *<p>
 * @author
 *<p>
 * create date：2016年4月25日 下午6:13:56 
 *</p>
  * @version 1.0  
*/
public class DateUtil {
    ContextLoaderListener  c;
    RequestContextListener d;
    CharacterEncodingFilter dd;
    DispatcherServlet dsss;
    InternalResourceViewResolver i;
    
    SqlSessionFactoryBean sl;
    MapperScannerConfigurer mmm;
}
