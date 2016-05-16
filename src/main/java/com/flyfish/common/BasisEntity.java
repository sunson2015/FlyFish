package com.flyfish.common;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * description: 
 *<p>
 * @author 
 *<p>
 * create date：2016年4月27日 下午6:57:34 
 *</p>
  * @version 1.0  
*/
public abstract class BasisEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8800409703357107528L;
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
