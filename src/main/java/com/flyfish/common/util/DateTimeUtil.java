package com.flyfish.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * description: 
 *<p>
 * @author 
 *<p>
 * create date：2016年5月16日 上午10:23:31 
 *</p>
  * @version 1.0  
*/
public class DateTimeUtil {
  public static boolean isValidateDate(String day){
      boolean converSuccess=true;
      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
      try {
          sdf.setLenient(false);
        sdf.parse(day);
    }
    catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        converSuccess=false;
    }
      return converSuccess;
  }
  
  public static boolean isLeapYear(int year){
      Calendar calendar =Calendar.getInstance();
      boolean b=((GregorianCalendar)calendar).isLeapYear(year);
      return b;
  }
  public static void main(String[] args) {
    System.out.println(DateTimeUtil.isLeapYear(2015));
  }
}
